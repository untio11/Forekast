package com.example.forekast;

abstract class RepositoryInterface {

    abstract WeatherInterface getWeather();
    abstract List<ClothingInterface> getClothing(ClothingType type,
                                                 ClothingCriteriaInterface criteria);
    abstract void addClothing(ClothingInterface clothing);
    abstract void removeClothing(ClothingInterface clothing);
}
