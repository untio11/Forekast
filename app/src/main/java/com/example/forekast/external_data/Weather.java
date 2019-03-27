package com.example.forekast.external_data;

public class Weather {

    private float temp; // In degrees celsius
    private float uv_index; // In standard units for this stuff
    private float precipitation; // in mm
    private float feels_like; // In degrees celsius
    private float wind; // In km/h
    private String city;

    /**
     * Set the city where the weather is polled
     * @param city As a string
     */
    void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    /**
     * Set the temperature
     * @param temp In degrees celsius
     */
    void setTemp(float temp) {
        this.temp = temp;
    }

    public float getTemp() {
        return temp;
    }

    /**
     * Set the expected rain for that day
     * @param precipitation In mm
     */
    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    /**
     * Set the feels like temperature
     * @param feels_like In degrees celsius
     */
    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public float getFeels_like() {
        return feels_like;
    }

    /**
     * Set the UV-index
     * @param uv_index In UV standard units (whatever that might be)
     */
    public void setUv_index(float uv_index) {
        this.uv_index = uv_index;
    }

    public float getUv_index() {
        return uv_index;
    }

    /**
     * Set the wind speed
     * @param wind In km/h
     */
    public void setWind(float wind) {
        this.wind = wind;
    }

    public float getWind() {
        return wind;
    }
}