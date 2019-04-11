package com.example.forekast.Suggestion;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.TorsoClothing;

public class Outfit {

    public TorsoClothing torso;
    public Clothing innerTorso;
    public Clothing outerTorso;
    public Clothing pants;
    public Clothing shoes;

    public Outfit(TorsoClothing torso, Clothing pants, Clothing shoes){
        this.torso = torso;
        this.pants = pants;
        this.shoes = shoes;
    }
}
