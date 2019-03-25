package com.example.forekast;

import com.example.forekast.external_data.Weather;

import javax.annotation.Nullable;

import androidx.lifecycle.Observer;

class MockLiveDataListener implements Observer<Weather> {
    @Override
    public void onChanged(@Nullable final Weather new_weather) {
        synchronized (this) {
            notifyAll();
        }
    }
}
