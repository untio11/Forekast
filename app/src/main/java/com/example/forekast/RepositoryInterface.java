package com.example.forekast;

import java.util.List;

abstract class RepositoryInterface {

    abstract WeatherInterface getWeather();
    abstract List<Clothing> getClothing(ClothingType type,
                                                 ClothingCriteriaInterface criteria);
    abstract void addClothing(Clothing clothing);
    abstract void removeClothing(Clothing clothing);
}
