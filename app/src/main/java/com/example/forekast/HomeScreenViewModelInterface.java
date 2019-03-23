package com.example.forekast;

import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


abstract class HomeScreenViewModelInterface {

    MutableLiveData<Outfit> currentOutfit;

    MutableLiveData<Weather> currentWeather;

    ClothingCriteria clothingCriteria;

    abstract LiveData<Outfit> getLiveOutfit();

    abstract LiveData<Weather> getLiveWeather();

    abstract ClothingCriteria getClothingCriteria();

    abstract ClothingCriteria setClothingCriteria(ClothingCriteria criteria);

    abstract void nextClothing(String clothing_type);

    abstract void previousClothing(String clothing_type );

    abstract void updateWeather();

    abstract void newOutfit();

    abstract void setCurrentOutfit(Outfit new_outfit);
}
