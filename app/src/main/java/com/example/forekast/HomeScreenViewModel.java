package com.example.forekast;

import androidx.lifecycle.LiveData;

class HomeScreenViewModel extends HomeScreenViewModelInterface {
    @Override
    LiveData<Outfit> getLiveOutfit() {
        return currentOutfit;
    }

    @Override
    LiveData<Weather> getLiveWeather() {
        return currentWeather;
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
        Repository.getWeather(currentWeather);
    }

    @Override
    void newOutfit() {

    }

    @Override
    void setCurrentOutfit(Outfit new_outfit) {

    }
}
