package com.example.forekast;

import android.arch.lifecycle.LiveData;


abstract class HomeScreenViewModelInterface {

    LiveData<Outfit> currentOutfit;

    LiveData<Weather> currentWeather;

    ClothingCriteria clothingCriteria;

    abstract LiveData<Outfit> getLiveOutfit();

    abstract LiveData<Weather> getLiveWeather();

    abstract ClothingCriteria getClothingCriteria();

    abstract ClothingCriteria setClothingCriteria(ClothingCriteria criteria);

    abstract void nextClothing(ClothingType clothing_type);

    abstract void previousClothing(ClothingType clothing_type );

    abstract void updateWeather();

    abstract void newOutfit();

    abstract void setCurrentOutfit(Outfit new_outfit);

    abstract Weather getWeather();

}
