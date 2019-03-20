package com.example.forekast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class OutfitPowersetInterface implements Iterable<List<Clothing>> {
    List<Clothing> inner_top;
    List<Clothing> outer_top;
    List<Clothing> pants;
    List<Clothing> shoes;

    private List<List<Clothing>> all = new ArrayList<>();

    OutfitPowersetInterface() {
        all.add(inner_top);
        all.add(outer_top);
        all.add(pants);
        all.add(shoes);
    }

    @Override
    public Iterator<List<Clothing>> iterator() {
        return all.iterator();
    }
}
