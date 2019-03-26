package com.example.forekast;

import android.util.Pair;

public class ClothingCriteria extends ClothingCriteriaInterface{

    MutablePair<Integer, Integer> warmth;
    MutablePair<Integer, Integer> formality;
    MutablePair<Integer, Integer> comfort;
    MutablePair<Integer, Integer> preference;
    String owner;

    ClothingCriteria(MutablePair<Integer, Integer> warmth, MutablePair<Integer, Integer> formality,
                     MutablePair<Integer, Integer> comfort, MutablePair<Integer, Integer> preference,
                     String owner){
        this.warmth = warmth;
        this.formality = formality;
        this.comfort = comfort;
        this.preference = preference;
        this.owner = owner;
    }
}
