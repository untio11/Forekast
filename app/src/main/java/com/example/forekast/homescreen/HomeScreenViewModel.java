package com.example.forekast.homescreen;

import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.example.forekast.outfits.Outfit;
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
    void nextClothing(String clothing_type) {

    }

    @Override
    void previousClothing(String clothing_type) {

    }

    @Override
    void updateWeather() {
        Repository.getWeather("Eindhoven", currentWeather);
    }

    @Override
    void newOutfit() {

    }

    @Override
    void setCurrentOutfit(Outfit new_outfit) {

    }
}
