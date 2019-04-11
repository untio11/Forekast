package com.example.forekast.clothing;

import androidx.room.Entity;

public class TorsoClothing extends Clothing{

    public static Clothing torso;
    public static Clothing inner;
    public static Clothing outer;

    public TorsoClothing(){
        super();
    }

    public TorsoClothing (Clothing innerOrOuter) {
        this.torso = innerOrOuter;
        this.inner = null;
        this.outer = null;
    }

    public TorsoClothing (Clothing inner, Clothing outer) {
        this.torso = null;
        this.inner = inner;
        this.outer = outer;
    }
}
