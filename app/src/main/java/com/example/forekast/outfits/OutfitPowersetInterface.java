package com.example.forekast.outfits;

import com.example.forekast.clothing.Clothing;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract public class OutfitPowersetInterface implements Iterable<List<Clothing>> {
    public List<Clothing> inner_top;
    public List<Clothing> outer_top;
    public List<Clothing> pants;
    public List<Clothing> shoes;

    private List<List<Clothing>> all = new ArrayList<>();

    OutfitPowersetInterface() {
        all.add(inner_top);
        all.add(outer_top);
        all.add(pants);
        all.add(shoes);
    }

    @NotNull
    @Override
    public Iterator<List<Clothing>> iterator() {
        return all.iterator();
    }
}
