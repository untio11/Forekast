package com.example.forekast;

import java.util.List;

abstract class SuggestionModuleInterface {

    ClothingCriteria currentCriteria;

    Weather currentWeather;

    abstract void setCurrentCriteria(ClothingCriteria criteria, Weather weather);

    abstract ClothingCriteria generateCriteria();

    abstract void generateOutfit(ClothingCriteria criteria);

    abstract Outfit getRandomOutfit();

    abstract Outfit next(ClothingType type);

    abstract Outfit previous(ClothingType type);

    abstract List<Clothing> getClothing(ClothingType type, ClothingCriteria criteria);
}
