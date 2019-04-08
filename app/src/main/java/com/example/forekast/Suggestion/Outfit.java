package com.example.forekast.Suggestion;

import com.example.forekast.clothing.Clothing;

public class Outfit {

    public Clothing inner_torso;
    public Clothing outer_torso;
    public Clothing pants;
    public Clothing shoes;

    public Outfit(Clothing inner_torso, Clothing outer_torso, Clothing pants, Clothing shoes){
        this.inner_torso = inner_torso;
        this.outer_torso = outer_torso;
        this.pants = pants;
        this.shoes = shoes;
    }
}
