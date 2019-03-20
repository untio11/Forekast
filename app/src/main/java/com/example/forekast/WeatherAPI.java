package com.example.forekast;

abstract class WeatherAPI extends WeatherAPIInterface {

    @Override
    Weather getWeather() {
        return null;
    }

    @Override
    protected Weather doInBackground(Void ... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Weather result) {
    }
}
