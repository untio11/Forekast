package com.example.forekast;

 abstract class HomeScreenViewModelInterface {

    abstract void getLiveOutfit();

    abstract LiveData<Weather> getLiveWeather();

    abstract ClothingCriteriaInterface getClothingCriteria();

    abstract ClothingCriteriaInterface setClothingCriteria(ClothingCriteriaInterface criteria);

    abstract void nextClothing(ClothingType clothing_type);

    abstract void previousClothing(ClothingType clothing_type );

    abstract void updateWeather();

    abstract void newOutfit();

    abstract void setCurrentOutfit(OutfitInterface new_outfit);

    abstract WeatherInterface getWeather();

}
