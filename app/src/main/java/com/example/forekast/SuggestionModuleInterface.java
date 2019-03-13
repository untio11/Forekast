package com.example.forekast;

import java.util.List;

abstract class SuggestionModuleInterface {

    abstract void generateOutfit(ClothingCriteria criteria);

    abstract Outfit getRandomOutfit();

    abstract Outfit next(ClothingType type);

    abstract Outfit previous(ClothingType type);

    abstract void setCurrentCriteria(ClothingCriteria criteria);

    abstract ClothingCriteria generateCriteria(Weather weather);

    abstract List<ClothingInterface> getClothing(ClothingType type, ClothingCriteria criteria);
}
