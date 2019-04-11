package com.example.forekast.clothing;

public class ClothingCriteria extends ClothingCriteriaInterface{

    public ClothingCriteria(MutablePair<Integer, Integer> warmth, MutablePair<Integer, Integer> formality,
                            MutablePair<Integer, Integer> comfort, MutablePair<Integer, Integer> preference,
                            String owner){
        this.warmth = warmth;
        this.formality = formality;
        this.comfort = comfort;
        this.preference = preference;
        this.owner = owner;
    }

    public ClothingCriteria() {
        super();
    }

    @Override
    public String toString() {
        return warmth.first + "+" + warmth.second + ", "+ formality.first + "+" + formality.second +
                ", " + comfort.first + "+" + comfort.second + ", " + preference.first + "+" + preference.second + ", " + owner;
    }

    public void expandRange() {
        warmth.first--;
        warmth.second++;

        formality.first--;
        formality.second++;

        comfort.first--;
        comfort.second++;

        preference.first--;
        // Upper bound preference does not have to increase, since it is always max.
    }
}
