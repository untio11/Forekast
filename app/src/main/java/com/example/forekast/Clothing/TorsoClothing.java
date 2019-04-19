package com.example.forekast.Clothing;

public class TorsoClothing {

    public final boolean one;
    public final boolean two;
    public Clothing torso;
    public Clothing inner;
    public Clothing outer;

    public TorsoClothing(Clothing innerOrOuter) {

        this.torso = innerOrOuter;

        one = true;
        two = false;

    }

    public TorsoClothing(Clothing inner, Clothing outer) {

        this.inner = inner;
        this.outer = outer;

        one = false;
        two = true;

    }
}
