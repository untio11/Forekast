package com.example.forekast.homescreen;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import androidx.lifecycle.LiveData;

public class HomeScreenViewModel extends HomeScreenViewModelInterface {
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
    public void setOwner(String new_owner) {
        clothingCriteria.owner = new_owner;
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
        return clothingCriteria.formality.second;
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
    public void updateWeather(Location location) {
        Repository.getWeather(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), currentWeather);
    }

    @Override
    public void updateWeather(String name) {
        Repository.getWeather(name, currentWeather);
    }

    @Override
    void newOutfit() {
        currentOutfit.postValue(sugg.getRandomOutfit());
    }
}
