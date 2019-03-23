package com.example.forekast.clothing;

import android.util.Pair;

abstract public class ClothingCriteria {

    Pair<Integer, Integer> warmth;
    Pair<Integer, Integer> formality;
    Pair<Integer, Integer> comfort;
    Pair<Integer, Integer> preference;
    String owner;
}
