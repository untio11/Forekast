package com.example.forekast;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingType;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

class Repository {

    static void getWeather(MutableLiveData<Weather> weather) {
        new WeatherAPI().execute(weather);
    }

    static List<Clothing> getClothing(ClothingType type, ClothingCriteria criteria) {
        return null;
    }

    static void addClothing(Clothing clothing) {

    }

    static void removeClothing(Clothing clothing) {

    }
}
