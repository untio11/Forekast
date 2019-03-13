package com.example.forekast;

import android.media.Image;

class ClothingProperties {
    long ID;
    String owner;
    ClothingType type;
    int comfort;
    int warmth;
    int formality;
    int preference;
    int[] color;
    boolean washing_machine;
    boolean last_washing_state;
    int washing_time;
    Image picture;

    @Override
    public String toString() {
        return owner + "'s " + type + " [" + ID + "]: (" + warmth + ", " + formality + ", " + comfort + ")";
    }
}