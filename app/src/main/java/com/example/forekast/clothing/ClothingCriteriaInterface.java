package com.example.forekast.clothing;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ClothingCriteriaInterface implements Iterable<Pair<String, Pair<Integer, Integer>>> {
    public Pair<Integer, Integer> warmth;
    public Pair<Integer, Integer> formality;
    public Pair<Integer, Integer> comfort;
    public Pair<Integer, Integer> preference;
    public String owner;

    private List<Pair<String, Pair<Integer, Integer>>> all;

    // Add all the criteria to a big list for the iteration
    ClothingCriteriaInterface() {
        all = new ArrayList<>();
        all.add(new Pair<>("warmth",     new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        all.add(new Pair<>("formality",  new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        all.add(new Pair<>("comfort",    new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        all.add(new Pair<>("preference", new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }

    @Override
    public Iterator<Pair<String, Pair<Integer, Integer>>> iterator() {
        return all.iterator();
    }
}
