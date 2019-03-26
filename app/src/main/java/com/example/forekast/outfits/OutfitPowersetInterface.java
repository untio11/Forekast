package com.example.forekast.Outfits;

import com.example.forekast.Suggestion.SuggestionModule;
import com.example.forekast.clothing.Clothing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract public class OutfitPowersetInterface implements Iterable<List<Clothing>> {
    SuggestionModule sm = new SuggestionModule();

    public List<Clothing> inner_top  = sm.getClothing("torso");;
    public List<Clothing> outer_top = sm.getClothing("torso");;
    public List<Clothing> bottoms = sm.getClothing("legs");
    public List<Clothing> shoes = sm.getClothing("feet");

    private List<List<Clothing>> all = new ArrayList<>();

    OutfitPowersetInterface() {
        all.add(inner_top);
        all.add(outer_top);
        all.add(bottoms);
        all.add(shoes);
    }


    @Override
    public Iterator<List<Clothing>> iterator() {
        return all.iterator();
    }
}
