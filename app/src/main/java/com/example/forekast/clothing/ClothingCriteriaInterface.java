package com.example.forekast.clothing;

import android.util.Pair;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Object used to store the clothing criteria for filtering in one place. Iterable.
 * Do not forget to set the owner of the clothing here as well, otherwise you won't get any clothes from the repo.
 */
public abstract class ClothingCriteriaInterface implements Iterable<Pair<String, ClothingCriteriaInterface.MutablePair<Integer, Integer>>> {
    public class MutablePair<L, R> {
        public L first;
        public R second;

        MutablePair(L first, R second) {
            this.first = first;
            this.second = second;
        }
    }
    public MutablePair<Integer, Integer> warmth;
    public MutablePair<Integer, Integer> formality;
    public MutablePair<Integer, Integer> comfort;
    public MutablePair<Integer, Integer> preference;
    public String owner;
    public boolean washingMachine;

    private List<Pair<String, MutablePair<Integer, Integer>>> all;

    // Add all the criteria to a big list for the iteration
    ClothingCriteriaInterface() {
        all = new ArrayList<>();
        formality =  new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        comfort =    new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        warmth =     new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        preference = new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

        all.add(new Pair<>("warmth",     warmth));
        all.add(new Pair<>("formality",  formality));
        all.add(new Pair<>("comfort",    comfort));
        all.add(new Pair<>("preference", preference));
    }

    /**
     * Return the iterator for the criteria
     * @return A nested pair such that (criterium_name, (lower_bound, upper_bound))
     */
    @NotNull
    @Override
    public Iterator<Pair<String, MutablePair<Integer, Integer>>> iterator() {
        return all.iterator();
    }
}
