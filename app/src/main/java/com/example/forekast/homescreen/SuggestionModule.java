package com.example.forekast.homescreen;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.example.forekast.outfits.Outfit;
import com.example.forekast.outfits.OutfitPowSet;

import java.util.Collections;
import java.util.List;

class SuggestionModule extends SuggestionModuleInterface {
    SuggestionModule() {
        outfits = new OutfitPowSet();
    }

    @Override
    void generateOutfit(ClothingCriteria criteria) {
        outfits.inner_top = Repository.getClothing("Torso", null);
        outfits.outer_top = Repository.getClothing("Torso", null);
        outfits.pants = Repository.getClothing("Legs", null);
        outfits.shoes = Repository.getClothing("Feet", null);
    }

    @Override
    Outfit getRandomOutfit() {
        if (outfits == null) {
            generateOutfit(currentCriteria);
        }

        Outfit result = new Outfit();
        shuffle();

        result.inner_torso = outfits.inner_top.get(0);
        result.outer_torso = outfits.outer_top.get(0);
        result.pants = outfits.pants.get(0);
        result.shoes = outfits.shoes.get(0);

        return result;
    }

    private void shuffle() {
        for (List<Clothing> category : outfits) {
            Collections.shuffle(category);
        }
    }

    @Override
    Outfit next(String type) {

        return null;
    }

    @Override
    Outfit previous(String type) {

        return null;
    }

    @Override
    void setCurrentCriteria(ClothingCriteria criteria) {

    }

    @Override
    ClothingCriteria generateCriteria(Weather weather) {

        return null;
    }

    @Override
    List<Clothing> getClothing(String type, ClothingCriteria criteria) {

        return null;
    }
}
