package com.example.forekast.external_data;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.lifecycle.MutableLiveData;

public class WeatherAPI extends AsyncTask<MutableLiveData<Weather>, Void, Weather> {
    // URL constants
    private static final String apikey = "APPID=0b9abbd80c094690566a12c404593543";
    private static final String baseurl = "http://api.openweathermap.org/data/2.5/";
    private static final String uv = "uvi?";
    private static final String forecast = "forecast?";

    // Location variables
    private static boolean using_coordinates;
    private static String city_name; // For manual location setting
    private static String current_latitude;
    private static String current_longitude;

    // Statically store the last weather that was fetched for caching
    private static Weather last_weather;
    // The livedata object to be updated
    private MutableLiveData<Weather> target;

    /**
     * Use this constructor if the weather data should be fetched by city name (no gps)
     * @param cityname name of the city, as entered by the user
     */
    public WeatherAPI(String cityname) {
        city_name = cityname;
        using_coordinates = false;
    }

    /**
     * Use this constructor if the weather data should be fetched by coordinates (yes gps)
     * @param latitude latitude as string
     * @param longitude longitude as string
     */
    public WeatherAPI(String latitude, String longitude) {
        current_latitude = latitude;
        current_longitude = longitude;
        using_coordinates = true;
    }

    /**
     * Make a new weatherobject of the current weather
     * @param weathers Please parse exactly one livedata weather object, any more will be ignored
     * @return the current weather.
     */
    @SafeVarargs
    @Override
    protected final Weather doInBackground(MutableLiveData<Weather> ... weathers) {
        MutableLiveData<Weather> weather = weathers[0];
        Weather result = new Weather();

        // Start of by just sending the last used weather, so something is on the screen.
        target = weather;
        if (target.getValue() == null || !target.getValue().toString().equals(last_weather.toString())) {
            target.postValue(last_weather);
        }


        try {
            setWeatherProperties(result);
        } catch (IOException e) { // Something probably went wrong with the connection, just use the last valid weather data.
            e.printStackTrace();
            result = last_weather;
        }

        return result;
    }

    /**
     * Set the properties of the weather object according to the weather data
     * @param weather The object to be modified
     * @throws IOException when something goes wrong with the internet connection.
     */
    private void setWeatherProperties(Weather weather) throws IOException {
        HttpURLConnection forecast_con = openCon(getForecastURL());
        JsonObject forecast_json = readResponse(forecast_con);

        JsonObject citydata = forecast_json.getAsJsonObject("city");
        JsonObject coordinates = citydata.get("coord").getAsJsonObject(); // Needed for the UV api call

        JsonArray weather_data = forecast_json.getAsJsonArray("list"); // For the weather data

        HttpURLConnection uv_con;
        if (current_latitude != null) {
            uv_con = openCon(getUvURL(current_latitude, current_longitude));
        } else {
            uv_con = openCon(getUvURL(coordinates.get("lat").getAsString(), coordinates.get("lon").getAsString()));
        }

        JsonObject uv_json = readResponse(uv_con);


        double temp =       getAvg("main", "temp", weather_data);
        double wind_speed =  getAvg("wind", "speed", weather_data); // m/s, taken care of later
        double humidity =   getAvg("main", "humidity", weather_data) / 100.0; // so it's in [0,1]
        // Slightly tweaked approximation from https://www.abc.net.au/news/2018-08-10/weather-feels-like-temperatures/10050622
        double feels_like =  temp + 0.33 * humidity - 0.6 * wind_speed - 3.0;

        weather.setCity(citydata.get("name").getAsString());
        weather.setTemp((float) temp);
        weather.setPrecipitation((float) getAvg("rain", "3h", weather_data));
        weather.setWind((float) (wind_speed * 3.6)); // To convert from m/s to km/h
        weather.setFeels_like((float) feels_like);
        weather.setUv_index(uv_json.get("value").getAsFloat());
    }

    /**
     * Set up an HttpURLConnection from a string url
     * @param url the url to set up a connection to
     * @return the connection object
     * @throws IOException when the connection cannot be opened
     */
    private HttpURLConnection openCon(String url) throws IOException {
        URL url_ = new URL(url);
        HttpURLConnection result = (HttpURLConnection) url_.openConnection();
        result.setRequestMethod("GET");
        return result;
    }

    /**
     * Return the response of the given connection as JSON object
     * @param con The connection where the request is sent to
     * @return A JSON object containing the response
     * @throws IOException If the connection is invalid: no inputstream can be generated
     */
    private JsonObject readResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        con.disconnect();
        in.close();
        return new JsonParser().parse(response.toString()).getAsJsonObject();
    }

    /**
     * Get the average value of an attribute in the weather data
     * @param cat the category to use
     * @param element the actual element in the category to take the average from
     * @param data the array containing the data
     * @return the average value of the passed element in the category
     */
    private double getAvg(String cat, String element, JsonArray data) {
        float result = 0;
        final int size = 3; // How many datasamples to take. This looks 3 entries ahead, which comes down to 9 hours

        for (int i = 0; i < size; i++) {
            try { // If the webapi does not have data on some weather attribute, it sets it to null, so we need to check for that
                result += data.get(i).getAsJsonObject().get(cat).getAsJsonObject().get(element).getAsFloat() / size;
            } catch (NullPointerException e) {
                result += 0;
            }
        }

        return result;
    }

    /**
     * Get the url for the forecast for the coming 5 days, every three hours. Units are in metric.
     * When the user chooses not to use the GPS, the given city name is used. The response is as good as the same
     * @return URL to the api
     */
    private String getForecastURL() {
        if (using_coordinates) {
            return baseurl + forecast + "lat=" + current_latitude + "&lon=" + current_longitude + "&" + apikey + "&units=metric";
        } else {
            return baseurl + forecast + "q=" + city_name + "&" + apikey + "&units=metric";
        }
    }

    /**
     * Get the current UV index API URL. Needs to be called with lat and long, but those might be unavailable when fetching without gps.
     * Luckily the forecast response also gives the lat and lon from a city name, so use those
     * @param lat latitude
     * @param lon longitude
     * @return A string containing the URL to call the uv-index api.
     */
    private String getUvURL(String lat, String lon) {
        return baseurl + uv + "lat=" + lat + "&lon=" + lon + "&" + apikey;
    }

    /**
     * update the last valid weather and post it to the livedata listener.
     * @param result the new weather object.
     */
    @Override
    protected void onPostExecute(Weather result) {
        last_weather = result;
        target.postValue(result);
    }
}
