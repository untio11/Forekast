package com.example.forekast.clothing;

import android.media.Image;
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

    @ColumnInfo(name = "type")
    public String type = "";

    @ColumnInfo(name = "location")
    public String location = "";

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

    @Ignore
    @ColumnInfo(name = "picture")
    public Image picture;

    public Clothing() {
        String us = this.getClass().getSimpleName();
        String father = this.getClass().getSuperclass().getSimpleName();

        if ("Clothing".equals(father)) {
            location = us;
        } else if (!"Object".equals(father)) {
            location = father;
            type = us;
        }
    }
}
