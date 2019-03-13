package com.example.forekast;

import java.util.List;

abstract class RepositoryInterface {

    abstract Weather getWeather();
    abstract List<ClothingInterface> getClothing(ClothingType type,
                                                 ClothingCriteria criteria);
    abstract void addClothing(ClothingInterface clothing);
    abstract void removeClothing(ClothingInterface clothing);
}
