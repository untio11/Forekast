package com.example.forekast.clothing;

public class ClothingCriteria extends ClothingCriteriaInterface{

    public MutablePair<Integer, Integer> warmth;
    public MutablePair<Integer, Integer> formality;
    public MutablePair<Integer, Integer> comfort;
    public MutablePair<Integer, Integer> preference;
    public String owner;

    public ClothingCriteria(MutablePair<Integer, Integer> warmth, MutablePair<Integer, Integer> formality,
                            MutablePair<Integer, Integer> comfort, MutablePair<Integer, Integer> preference,
                            String owner){
        this.warmth = warmth;
        this.formality = formality;
        this.comfort = comfort;
        this.preference = preference;
        this.owner = owner;
    }
}
