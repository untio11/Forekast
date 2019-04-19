package com.example.forekast.ExternalData;

import org.jetbrains.annotations.NotNull;

public class Weather {

    public float temp; // In degrees celsius
    public float uv_index; // In standard units for this stuff
    public float precipitation; // in mm
    public float feels_like; // In degrees celsius
    public float wind; // In km/h
    private String city; // Name of the current location
    private String weather_desc; // Short description of the weather

    public String getCity() {
        return city;
    }

    /**
     * Set the city where the weather is polled
     *
     * @param city As a string
     */
    void setCity(String city) {
        this.city = city;
    }

    public float getTemp() {
        return temp;
    }

    /**
     * Set the temperature
     *
     * @param temp In degrees celsius
     */
    void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    /**
     * Set the expected rain for that day
     *
     * @param precipitation In mm
     */
    void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getFeels_like() {
        return feels_like;
    }

    /**
     * Set the feels like temperature
     *
     * @param feels_like In degrees celsius
     */
    void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public float getUv_index() {
        return uv_index;
    }

    /**
     * Set the UV-index
     *
     * @param uv_index In UV standard units (whatever that might be)
     */
    void setUv_index(float uv_index) {
        this.uv_index = uv_index;
    }

    public float getWind() {
        return wind;
    }

    /**
     * Set the wind speed
     *
     * @param wind In km/h
     */
    void setWind(float wind) {
        this.wind = wind;
    }

    public String getWeather_desc() {
        return weather_desc;
    }

    /**
     * Set a short description of the current weather conditions.
     *
     * @param weather_desc description
     */
    void setWeather_desc(String weather_desc) {
        this.weather_desc = weather_desc;
    }

    @NotNull
    @Override
    public String toString() {
        return city + ": " + weather_desc + ", " + temp + " Degrees, feels like: " + feels_like;
    }
}
