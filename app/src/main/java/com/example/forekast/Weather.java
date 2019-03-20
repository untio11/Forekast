package com.example.forekast;

class Weather {

    private int temp;
    private int uv_index;
    private int precipitation;
    private int weather_icon;
    private int feels_like;
    private int wind;
    private String city;

    Weather(int temp, int uv_index, int precipitation, int weather_icon, int feels_like, int wind,
            String city) {
        this.temp = temp;
        this.uv_index = uv_index;
        this.precipitation = precipitation;
        this.weather_icon = weather_icon;
        this.feels_like = feels_like;
        this.wind = wind;
        this.city = city;
    }
}
