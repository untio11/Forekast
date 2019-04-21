package com.example.forekast.Clothing;

import android.util.Pair;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Object used to store the clothing criteria for filtering in one place. Iterable.
 * Do not forget to set the owner of the clothing here as well, otherwise you won't get any clothes from the repo.
 */
public class ClothingCriteria implements Iterable<Pair<String, ClothingCriteria.MutablePair<Integer, Integer>>> {
    private List<Pair<String, MutablePair<Integer, Integer>>> all;
    public MutablePair<Integer, Integer> warmth;
    public MutablePair<Integer, Integer> formality;
    public MutablePair<Integer, Integer> comfort;
    public MutablePair<Integer, Integer> preference;
    public String owner;
    public boolean washingMachine = false;

    /**
     * Ensure that the internal data representations are correctly linked for the iteration.
     */
    private void init() {
        this.all = new ArrayList<>();
        this.formality = new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.comfort = new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.warmth = new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.preference = new MutablePair<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

        this.all.add(new Pair<>("warmth", warmth));
        this.all.add(new Pair<>("formality", formality));
        this.all.add(new Pair<>("comfort", comfort));
        this.all.add(new Pair<>("preference", preference));
    }

    public ClothingCriteria() {
        init();
    }

    /**
     * Makes a copy of this object without keeping references.
     * @return A copy of this.
     */
    public ClothingCriteria copy() {
        ClothingCriteria result = new ClothingCriteria();
        result.owner = this.owner;
        result.warmth = new MutablePair<>(5, 5);
        result.comfort = new MutablePair<>(5, 5);
        result.formality = new MutablePair<>(5, 5);
        result.preference = new MutablePair<>(10, 10);
        result.washingMachine = this.washingMachine;

        return result;
    }

    /**
     * Return the iterator for the criteria
     *
     * @return A nested pair such that (criterium_name, (lower_bound, upper_bound))
     */
    @NotNull
    @Override
    public Iterator<Pair<String, MutablePair<Integer, Integer>>> iterator() {
        return all.iterator();
    }

    public static class MutablePair<L, R> {
        public L first;
        public R second;

        public MutablePair(L first, R second) {
            this.first = first;
            this.second = second;
        }

        public void setBoth(L first, R second) {
            this.first = first;
            this.second = second;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return warmth.first + "+" + warmth.second + ", " + formality.first + "+" + formality.second +
                ", " + comfort.first + "+" + comfort.second + ", " + preference.second + ", " + owner;
    }
}
