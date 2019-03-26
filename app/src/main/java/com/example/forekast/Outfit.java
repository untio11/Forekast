package com.example.forekast;

public class Outfit {

    Clothing inner_torso;
    Clothing outer_torso;
    Clothing pants;
    Clothing shoes;
    Boolean coat;
    Boolean gloves;
    Boolean umbrella;
    Boolean sunglasses;
    Boolean leggings;

    Outfit(Clothing inner_torso, Clothing outer_torso, Clothing pants, Clothing shoes, Boolean coat, Boolean gloves, Boolean umbrella, Boolean sunglasses, Boolean leggings){
        this.inner_torso = inner_torso;
        this.outer_torso = outer_torso;
        this.pants = pants;
        this.shoes = shoes;
        this.coat = coat;
        this.gloves = gloves;
        this.umbrella = umbrella;
        this.sunglasses = sunglasses;
        this.leggings = leggings;
    }
}
