package com.example.forekast.external_data;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
        target.postValue(last_weather);


        try {
            setWeatherProperties(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void setWeatherProperties(Weather weather) throws IOException {
        URL forecast_url = new URL(getForecastURL());
        HttpURLConnection forecast_con = (HttpURLConnection) forecast_url.openConnection();
        forecast_con.setRequestMethod("GET");

        int status = forecast_con.getResponseCode();

        BufferedReader forecast_in = new BufferedReader(new InputStreamReader(forecast_con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = forecast_in.readLine()) != null) {
            response.append(inputLine);
        }

        forecast_in.close();
        forecast_con.disconnect();

        JsonObject forecast_json = new JsonParser().parse(response.toString()).getAsJsonObject();
        JsonObject citydata = forecast_json.getAsJsonObject("city");
        JsonArray weather_data = forecast_json.getAsJsonArray("list");

        JsonObject coordinates = citydata.get("coord").getAsJsonObject();
        URL uv_indexurl = new URL(getUvURL(coordinates.get("lat").getAsString(), coordinates.get("lon").getAsString()));


        weather.setCity(citydata.get("name").getAsString());
        weather.setTemp(getAvgTemp(weather_data));
    }

    private float getAvgTemp(JsonArray data) {
        float result = 0;
        final int size = 3; // How many datasamples to take

        for (int i = 0; i < size; i++) {
            JsonObject datapoint = data.get(i).getAsJsonObject();
            result += 1.0/size * datapoint.get("main").getAsJsonObject().get("temp").getAsFloat();
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
        return baseurl + forecast + "lat=" + lat + "&lon=" + lon + "&" + apikey;
    }

    @Override
    protected void onPostExecute(Weather result) {
        last_weather = result;
        target.postValue(result);
    }
}
