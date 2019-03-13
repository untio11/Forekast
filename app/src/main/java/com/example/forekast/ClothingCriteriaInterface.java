package com.example.forekast;

import android.util.Pair;

abstract class ClothingCriteriaInterface {

    Pair<Integer, Integer> warmth;
    Pair<Integer, Integer> formality;
    Pair<Integer, Integer> comfort;
    Pair<Integer, Integer> preference;
    String owner;
}
