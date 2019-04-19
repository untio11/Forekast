package com.example.forekast.Suggestion;

import com.example.forekast.Clothing.Clothing;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class OutfitPowerset implements Iterable<List<Clothing>> {
    private final List<List<Clothing>> all = new ArrayList<>();
    public List<Clothing> inner_torso;
    public List<Clothing> outer_torso;
    public List<Clothing> bottoms;
    public List<Clothing> shoes;

    OutfitPowerset() {
        inner_torso = new ArrayList<>();
        outer_torso = new ArrayList<>();
        bottoms = new ArrayList<>();
        shoes = new ArrayList<>();
        set();
    }

    private void set() {
        all.add(inner_torso);
        all.add(outer_torso);
        all.add(bottoms);
        all.add(shoes);
    }

    @NotNull
    @Override
    public Iterator<List<Clothing>> iterator() {
        return all.iterator();
    }
}
