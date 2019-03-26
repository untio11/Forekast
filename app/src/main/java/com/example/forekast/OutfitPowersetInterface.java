package com.example.forekast;

import java.util.List;

public class OutfitPowersetInterface {

    List<Clothing> inner_top;
    List<Clothing> outer_top;
    List<Clothing> bottoms;
    List<Clothing> shoes;

    ClothingCriteria criteria;

    OutfitPowersetInterface() {
        inner_top = SuggestionModule.getClothing("inner_top", criteria);
        outer_top = SuggestionModule.getClothing("outer_top", criteria);
        bottoms = SuggestionModule.getClothing("bottoms", criteria);
        shoes = SuggestionModule.getClothing("shoes", criteria);
    }

}
