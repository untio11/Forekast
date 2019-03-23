package com.example.forekast;

import android.media.Image;
import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Clothing {
    @PrimaryKey
    public long ID;

    @ColumnInfo(name = "owner")
    public String owner;

    @Ignore
    @ColumnInfo(name = "type")
    public ClothingType type;

    @ColumnInfo(name = "comfort")
    public int comfort;

    @ColumnInfo(name = "warmth")
    public int warmth;

    @ColumnInfo(name = "Formality")
    public int formality;

    @ColumnInfo(name = "preference")
    public int preference;

    @Ignore
    @ColumnInfo(name = "color")
    public int[] color;

    @ColumnInfo(name = "washing_machine")
    public boolean washing_machine;

    @ColumnInfo(name = "last_washing_state")
    public boolean last_washing_state;

    @ColumnInfo(name = "washing_time")
    public int washing_time;

    @ColumnInfo(name = "picture")
    public String picture;

    public void setImageUrl(String url) {
        this.picture = url;
    }

    Clothing(String owner, ClothingType type, int comfort, int warmth, int formality, int preference, int[] color, boolean washing_machine, boolean last_washing_state, int washing_time, String picture) {
        this.owner = owner;
        this.type = type;
        this.comfort = comfort;
        this.warmth = warmth;
        this.formality = formality;
        this.preference = preference;
        this.color = color;
        this.washing_machine = washing_machine;
        this.last_washing_state = last_washing_state;
        this.washing_time = washing_time;
        this.picture = picture;
    }

    Clothing() {
        // Just for now
    }
}
