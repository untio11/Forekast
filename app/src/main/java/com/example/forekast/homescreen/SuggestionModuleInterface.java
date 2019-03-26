package com.example.forekast.homescreen;

import com.example.forekast.external_data.Weather;
import com.example.forekast.outfits.Outfit;
import com.example.forekast.outfits.OutfitPowersetInterface;

import java.util.List;

abstract class SuggestionModuleInterface {

    ClothingCriteria currentCriteria;

    Weather currentWeather;

    OutfitPowersetInterface outfits;

    abstract void generateOutfit(ClothingCriteria criteria);

    abstract Outfit getRandomOutfit();

    abstract Outfit next(String type);

    abstract Outfit previous(String type);

    abstract void setCurrentCriteria(ClothingCriteria criteria);

    abstract ClothingCriteria generateCriteria(Weather weather);

    abstract List<Clothing> getClothing(String type, ClothingCriteria criteria);
}
