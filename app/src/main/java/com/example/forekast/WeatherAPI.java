package com.example.forekast;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

class WeatherAPI extends AsyncTask<MutableLiveData<Weather>, Void, Weather> {

    // Statically store the last weather that was fetched for caching
    private static Weather last_weather;
    // The livedata object to be updated
    private MutableLiveData<Weather> target;

    /**
     * Make a new weatherobject of the current weather
     * @param weathers Please parse exactly one livedata weather object, any more will be ignored
     * @return the current weather.
     */
    @Override
    protected Weather doInBackground(MutableLiveData<Weather> ... weathers) {
        MutableLiveData<Weather> weather = weathers[0];

        weather.postValue(last_weather);
        target = weather;

        Weather result = null;
        return result;
    }

    @Override
    protected void onPostExecute(Weather result) {
        last_weather = result;
        target.postValue(result);
    }
}
