package com.example.forekast.Suggestion;

import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.ExternalData.Weather;

abstract class SuggestionModuleInterface {

    ClothingCriteria criteria;

    Weather weather;

    OutfitPowerset outfits = new OutfitPowerset();

    abstract void setCurrentCriteria(ClothingCriteria criteria, Weather weather);

    abstract void generateOutfit();

    abstract Outfit setOutfit();

    public abstract Outfit refresh();

    public abstract Outfit next(String location);

    public abstract Outfit previous(String location);

    //abstract List<Clothing> getClothing(String location);
}
