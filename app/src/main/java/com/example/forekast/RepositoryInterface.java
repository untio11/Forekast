package com.example.forekast;

import java.util.List;

abstract class RepositoryInterface {

    abstract Weather getWeather();
    abstract List<Clothing> getClothing(ClothingType type,
                                                 ClothingCriteria criteria);
    abstract void addClothing(Clothing clothing);
    abstract void removeClothing(Clothing clothing);
}
