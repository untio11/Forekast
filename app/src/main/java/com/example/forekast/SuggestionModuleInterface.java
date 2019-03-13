package com.example.forekast;

import java.util.List;

abstract class SuggestionModuleInterface {

    abstract void generateOutfit(ClothingCriteriaInterface criteria);

    abstract OutfitInterface getRandomOutfit();

    abstract OutfitInterface next(ClothingType type);

    abstract OutfitInterface previous(ClothingType type);

    abstract void setCurrentCriteria(ClothingCriteriaInterface criteria);

    abstract ClothingCriteriaInterface generateCriteria(WeatherInterface weather);

    abstract List<ClothingInterface> getClothing(ClothingType type, ClothingCriteriaInterface criteria);
}
