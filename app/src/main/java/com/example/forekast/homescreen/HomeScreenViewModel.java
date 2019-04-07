package com.example.forekast.homescreen;

import android.os.AsyncTask;
import android.view.View;

import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    void setWarmth(int new_warmth) {
        clothingCriteria.warmth.second = new_warmth;
    }

    @Override
    int getWarmth() {
        return (clothingCriteria.warmth.second == Integer.MAX_VALUE ? 3 : clothingCriteria.warmth.second);
    }

    @Override
    void setComfort(int new_comfort) {
        clothingCriteria.comfort.second = new_comfort;
    }

    @Override
    int getComfort() {
        return (clothingCriteria.comfort.second == Integer.MAX_VALUE ? 3 : clothingCriteria.comfort.second);
    }

    @Override
    void setFormality(int new_formality) {
        clothingCriteria.formality.second = new_formality;
    }

    @Override
    int getFormality() {
        return (clothingCriteria.formality.second == Integer.MAX_VALUE ? 3 : clothingCriteria.formality.second);
    }

    @Override
    void nextClothing(String clothing_type) {
        currentOutfit.postValue(sugg.next(clothing_type));
    }

    @Override
    void previousClothing(String clothing_type) { currentOutfit.postValue(sugg.previous(clothing_type));
    }

    @Override
    void refreshClothing() { currentOutfit.postValue(sugg.refresh());}

    @Override
    void updateWeather() {
        Repository.getWeather("Eindhoven", currentWeather);
    }

    @Override
    void newOutfit() {
        currentOutfit.postValue(sugg.getRandomOutfit());
    }

    @Override
    boolean[] getAccessories() {
        boolean[] accessories = sugg.getAccessories();
        return accessories;
    }
}
