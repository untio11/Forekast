package com.example.forekast.Suggestion;

import com.example.forekast.Outfits.Outfit;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Weather;

import java.util.List;

abstract class SuggestionModuleInterface {

    ClothingCriteria currentCriteria;

    Weather currentWeather;

    abstract void setCurrentCriteria(ClothingCriteria criteria, Weather weather);

    abstract ClothingCriteria generateCriteria();

    abstract void generateOutfit(ClothingCriteria criteria);

    abstract Outfit getRandomOutfit();

    abstract Outfit next(String location);

    abstract Outfit previous(String location);

    abstract List<Clothing> getClothing(String location);
}
