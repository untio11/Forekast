package com.example.forekast;

import android.os.AsyncTask;

abstract class WeatherAPIInterface extends AsyncTask<Void, Void, Weather> {
    abstract Weather getWeather();
}