package com.example.forekast.homescreen;

import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import androidx.lifecycle.LiveData;

class HomeScreenViewModel extends HomeScreenViewModelInterface {

    @Override
    LiveData<Outfit> getLiveOutfit() {
        return currentOutfit;
    }

    @Override
    LiveData<Weather> getLiveWeather() { return currentWeather; }

    @Override
    ClothingCriteria getClothingCriteria() {
        return clothingCriteria;
    }

    @Override
    void setClothingCriteria(ClothingCriteria criteria) {
        this.clothingCriteria = criteria;
    }

    @Override
    void nextClothing(String clothing_type) {
        currentOutfit.postValue(sugg.next(clothing_type));
    }

    @Override
    void previousClothing(String clothing_type) {
        currentOutfit.postValue(sugg.previous(clothing_type));
    }

    @Override
    void updateWeather() {
        Repository.getWeather("Eindhoven", currentWeather);
    }

    @Override
    void newOutfit() {
        currentOutfit.postValue(sugg.getRandomOutfit());
    }
}
