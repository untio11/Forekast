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

    ClothingProperties() {}

    ClothingProperties(long ID, String owner, ClothingType type, int warmth, int formality,
                       int comfort, int preference, int[] color, boolean washing_machine,
                       boolean last_washing_state, int washing_time, Image picture) {
        this.ID = ID;
        this.owner = owner;
        this.type = type;
        this.warmth = warmth;
        this.formality = formality;
        this.comfort = comfort;
        this.preference = preference;
        this.color = color;
        this.washing_machine = washing_machine;
        this.last_washing_state = last_washing_state;
        this.washing_time = washing_time;
        this.picture = picture;
    }
}