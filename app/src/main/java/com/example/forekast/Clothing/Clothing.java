package com.example.forekast.Clothing;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * Main data of our app. Never manually set location or type! Just use the proper subclass and it will be set for you!
 */
@Entity
public class Clothing {
    /**
     * Do not manually change this variable, it will break the system
     * <p>
     * The ID number of the clothing
     */
    @PrimaryKey(autoGenerate = true)
    public long ID;

    /**
     * Do not manually change this variable, it will break the system
     * <p>
     * Owner of this piece of clothing (warderobe owner)
     */
    @ColumnInfo(name = "owner")
    public String owner;

    /**
     * The type of clothing (We do set the type manually now, different classes are ignored)
     */
    @ColumnInfo(name = "type")
    public String type = "";

    /**
     * Do not manually change this variable, it will break the system
     */
    @ColumnInfo(name = "location")
    public String location = "";

    /**
     * The comfort of this piece
     */
    @ColumnInfo(name = "comfort")
    public int comfort = 5;

    /**
     * The warmth of this piece
     */
    @ColumnInfo(name = "warmth")
    public int warmth = 5;

    /**
     * The formality of this piece
     */
    @ColumnInfo(name = "Formality")
    public int formality = 5;

    /**
     * How much this piece is liked
     */
    @ColumnInfo(name = "preference")
    public int preference = 5;

    /**
     * RGB array: [Red, Green, Blue]
     */
    @Ignore
    @ColumnInfo(name = "color")
    public int[] color;

    /**
     * Whether the piece is in the laundry machine
     */
    @ColumnInfo(name = "washing_machine")
    public boolean washing_machine = false;

    /**
     * How long this piece of clothing is gone for
     */
    @ColumnInfo(name = "washing_time")
    public long washing_time = 0;

    /**
     * This item can be worn over other items
     */
    @ColumnInfo(name = "overwearable")
    public boolean overwearable = false;

    /**
     * This item can be worn under other items
     */
    @ColumnInfo(name = "underwearable")
    public boolean underwearable = false;

    /**
     * Bitmap converted to array of bytes
     */
    @ColumnInfo(name = "picture")
    public byte[] picture;

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
        } else if ("Object".equals(father)) {
            location = "";
            type = "";
        }
    }

    /**
     * It's always a good idea to have a string representation of your class. Basic, quick overview of the piece of clothing
     *
     * @return [ID] owner::location::type - (warmth,formality,comfort)
     */
    @NotNull
    @Override
    public String toString() {
        return "[" + ID + "] " + owner + "::" + location + "::" + type + " - " + "(" + warmth + "," + formality + "," + comfort + ")";
    }

    /**
     * Presets the comfort, warmth and formality attributes when adding a piece of clothing,
     * as well as the over- and underwearable values for the Torso clothing
     *
     * @throws IllegalArgumentException if not the right instance of Torso, Legs or Feet was created
     *                                  or if the type that was given is not defined in the Torso class.
     */
    public void preSet() throws IllegalArgumentException {
        throw new IllegalArgumentException("Location was not defined");
    }

    /**
     * Resets the underwearable and overwearable attributes when an torso type was changed
     * Does nothing for Legs or Feet.
     *
     * @throws IllegalArgumentException if the type that was given is not defined in the Torso class.
     */
    public void setWearable() throws IllegalArgumentException {
        System.out.println("Changing clothing type");
        switch (type) {
            case "T-Shirt":
                underwearable = true;
                overwearable = false;
                break;
            case "Dress":
                underwearable = true;
                overwearable = false;
                break;
            case "Jacket":
                underwearable = false;
                overwearable = true;
                break;
            case "Shirt":
                underwearable = true;
                overwearable = true;
                break;
            case "Sweater":
                underwearable = false;
                overwearable = true;
                break;
            case "Tanktop":
                underwearable = true;
                overwearable = false;
                break;
            default:
                throw new IllegalArgumentException("This type is not defined in Torso class");
        }
    }
}
