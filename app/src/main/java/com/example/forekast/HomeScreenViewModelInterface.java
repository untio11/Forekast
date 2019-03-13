package com.example.forekast;

 abstract class HomeScreenViewModelInterface {

    abstract void  getLiveOutfit();

    abstract void getLiveWeather();

    abstract void getClothingCriteria();

    abstract void setClothingCriteria();

    abstract void nextClothing();

    abstract void previousClothing();

    abstract void updateWeather();

    abstract void newOutfit();

    abstract void setCurrentOutfit();

    abstract void getWeather();

}
