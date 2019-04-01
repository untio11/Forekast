package com.example.forekast.clothing;

import android.util.Pair;

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
        this.all = new ArrayList<>();
        this.formality =  new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.comfort =    new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.warmth =     new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.preference = new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

        this.all.add(new Pair<>("warmth",     warmth));
        this.all.add(new Pair<>("formality",  formality));
        this.all.add(new Pair<>("comfort",    comfort));
        this.all.add(new Pair<>("preference", preference));
    }

    /**
     * Return the iterator for the criteria
     * @return A nested pair such that (criterium_name, (lower_bound, upper_bound))
     */
    @Override
    public Iterator<Pair<String, MutablePair<Integer, Integer>>> iterator() {
        return all.iterator();
    }
}
