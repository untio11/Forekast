package com.example.forekast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class SuggestionModule extends SuggestionModuleInterface {
    SuggestionModule() {
        outfits = new OutfitPowSet();
    }

    @Override
    void generateOutfit(ClothingCriteria criteria) {
        outfits.inner_top = Repository.getClothing(ClothingType.TANKTOP, null);
        outfits.outer_top = Repository.getClothing(ClothingType.JACKET, null);
        outfits.pants = Repository.getClothing(ClothingType.SHOES, null);
        outfits.shoes = Repository.getClothing(ClothingType.BOOTS, null);
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
    Outfit next(ClothingType type) {

        return null;
    }

    @Override
    Outfit previous(ClothingType type) {

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
    List<Clothing> getClothing(ClothingType type, ClothingCriteria criteria) {

        return null;
    }
}
