package com.example.forekast;

import java.util.List;

abstract class SuggestionModuleInterface {

    ClothingCriteria currentCriteria;

    Weather currentWeather;

    OutfitPowersetInterface outfits;

    abstract void generateOutfit(ClothingCriteria criteria);

    abstract Outfit getRandomOutfit();

    abstract Outfit next(ClothingType type);

    abstract Outfit previous(ClothingType type);

    abstract void setCurrentCriteria(ClothingCriteria criteria);

    abstract ClothingCriteria generateCriteria(Weather weather);

    abstract List<Clothing> getClothing(ClothingType type, ClothingCriteria criteria);
}
