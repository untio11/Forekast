package com.example.forekast.Suggestion;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.TorsoClothing;

public class Outfit {

    public final TorsoClothing torso;
    public final Clothing pants;
    public final Clothing shoes;
    public Clothing innerTorso;
    public Clothing outerTorso;

    public Outfit(TorsoClothing torso, Clothing pants, Clothing shoes) {
        this.torso = torso;
        this.pants = pants;
        this.shoes = shoes;
    }
}
