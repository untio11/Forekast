package com.example.forekast.clothing;

public class TorsoClothing {

    public Clothing torso;
    public Clothing inner;
    public Clothing outer;

    public boolean one;
    public boolean two;

    public TorsoClothing (Clothing innerOrOuter) {

        this.torso = innerOrOuter;

        one = true;
        two = false;

    }

    public TorsoClothing (Clothing inner, Clothing outer) {

        this.inner = inner;
        this.outer = outer;

        one = false;
        two = true;

    }
}
