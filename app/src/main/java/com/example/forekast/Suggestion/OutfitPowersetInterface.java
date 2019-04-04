package com.example.forekast.Suggestion;

import com.example.forekast.Suggestion.SuggestionModule;
import com.example.forekast.clothing.Clothing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class OutfitPowersetInterface implements Iterable<List<Clothing>> {

    //SuggestionModule sm = new SuggestionModule();

    public List<Clothing> inner_torso;// = sm.getClothing("torso");;
    public List<Clothing> outer_torso;// = sm.getClothing("torso");;
    public List<Clothing> bottoms;// = sm.getClothing("legs");
    public List<Clothing> shoes;// = sm.getClothing("feet");


    private List<List<Clothing>> all = new ArrayList<>();

    public OutfitPowersetInterface() {
        inner_torso = new ArrayList<>();
        outer_torso = new ArrayList<>();
        bottoms = new ArrayList<>();
        shoes = new ArrayList<>();
    }

    public void set() {
        all.add(inner_torso);
        all.add(outer_torso);
        all.add(bottoms);
        all.add(shoes);
    }


    @Override
    public Iterator<List<Clothing>> iterator() {
        return all.iterator();
    }
}
