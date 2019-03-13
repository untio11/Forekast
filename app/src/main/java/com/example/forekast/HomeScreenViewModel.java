package com.example.forekast;

import android.arch.lifecycle.LiveData;

class HomeScreenViewModel extends HomeScreenViewModelInterface {

    WeatherAPIInterface weatherAPI = new WeatherAPI();

    @Override
    LiveData<Outfit> getLiveOutfit() {
        return null;
    }

    @Override
    LiveData<Weather> getLiveWeather() {
        return null;
    }

    @Override
    ClothingCriteria getClothingCriteria() {
        return null;
    }

    @Override
    ClothingCriteria setClothingCriteria(ClothingCriteria criteria) {
        return null;
    }

    @Override
    void nextClothing(ClothingType clothing_type) {

    }

    @Override
    void previousClothing(ClothingType clothing_type) {

    }

    @Override
    void updateWeather() {

    }

    @Override
    void newOutfit() {

    }

    @Override
    void setCurrentOutfit(Outfit new_outfit) {

    }

    @Override
    Weather getWeather() {
        return weatherAPI.getWeather();
    }
}
