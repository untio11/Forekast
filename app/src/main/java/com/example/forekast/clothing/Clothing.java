package com.example.forekast.clothing;

import android.media.Image;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Clothing {
    /**
     * Do not manually change this variable, it will break the system
     */
    @PrimaryKey(autoGenerate = true)
    public long ID;

    /**
     * Do not manually change this variable, it will break the system
     */
    @ColumnInfo(name = "owner")
    public String owner;

    /**
     * Do not manually change this variable, it will break the system
     */
    @ColumnInfo(name = "type")
    public String type = "";

    /**
     * Do not manually change this variable, it will break the system
     */
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

    @ColumnInfo(name = "overwearable")
    public boolean overwearable;

    @ColumnInfo(name = "underwearable")
    public boolean underwearable;

    @ColumnInfo(name = "picture")
    public String picture;

    public Clothing() {
        setLocAndTyp();
    }

    /**
     * Set the location and type of a subclass of clothing based on the parent class.
     */
    private void setLocAndTyp() {
        // Only do this if it has not been set yet.
        if (!location.equals("") || !type.equals("")) {
            return;
        }

        String us = this.getClass().getSimpleName(); // Get our own class name
        String father = this.getClass().getSuperclass().getSimpleName(); // Get our parent class name

        if ("Clothing".equals(father)) {
            // If the parent class is "Clothing", this class is a location (should never happen)
            location = us;
        } else if (!"Object".equals(father)) {
            // If the parent class is not "Object", that must mean it's a location, so set
            // the location of this piece to our parents class name and the type to our own name
            location = father;
            type = us;
        }
    }

    /**
     * It's always a good idea to have a string representation of your class. Basic, quick overview of the piece of clothing
     * @return [ID] owner::location::type - (warmth,formality,comfort)
     */
    @Override
    public String toString() {
        return "[" + ID + "] " + owner + "::" + location + "::" + type + " - " + "(" + warmth + "," + formality + "," + comfort + ")";
    }

    public void setImageUrl(String url) {
        this.picture = url;
    }

    public Clothing(String owner, String type, int comfort, int warmth, int formality, int preference, int[] color, boolean washing_machine, boolean last_washing_state, int washing_time, String picture) {
        setLocAndTyp();
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

    public Clothing(String type) {
        setLocAndTyp();
        this.type = type;
    }
}
