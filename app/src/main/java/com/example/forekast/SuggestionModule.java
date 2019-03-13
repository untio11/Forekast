package com.example.forekast;

import java.util.List;

class SuggestionModule extends SuggestionModuleInterface {
    @Override
    void generateOutfit(ClothingCriteria criteria) {

    }

    @Override
    Outfit getRandomOutfit() {
        return null;
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
