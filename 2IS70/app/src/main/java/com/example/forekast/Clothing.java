package com.example.forekast;

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

    @Ignore
    @ColumnInfo(name = "picture")
    public Image picture;

}
