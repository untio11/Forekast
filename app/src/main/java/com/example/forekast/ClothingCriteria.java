package com.example.forekast;

import android.util.Pair;

public class ClothingCriteria {

    int warmth;
    int formality;
    int comfort;
    int preference;
    String owner;

    ClothingCriteria(int warmth, int formality, int comfort, int preference, String owner){
        this.warmth = warmth;
        this.formality = formality;
        this.comfort = comfort;
        this.preference = preference;
        this.owner = owner;
    }
}
