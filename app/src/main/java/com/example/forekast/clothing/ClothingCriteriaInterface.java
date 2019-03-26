package com.example.forekast.clothing;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Object used to store the clothing criteria for filtering in one place. Iterable.
 * Do not forget to set the owner of the clothing here as well, otherwise you won't get any clothes from the repo.
 */
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
        formality = new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        comfort = new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        warmth = new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        preference = new Pair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

        all.add(new Pair<>("warmth",     warmth));
        all.add(new Pair<>("formality",  formality));
        all.add(new Pair<>("comfort",    comfort));
        all.add(new Pair<>("preference", preference));
    }

    /**
     * Return the iterator for the criteria
     * @return A nested pair such that (criterium_name, (lower_bound, upper_bound))
     */
    @Override
    public Iterator<Pair<String, Pair<Integer, Integer>>> iterator() {
        return all.iterator();
    }
}
