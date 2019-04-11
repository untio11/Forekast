package com.example.forekast.clothing;

public class Feet extends Clothing {

    @Override
    public void preSet() {
        // Get presettings when adding Feet clothing
        // Default value is 5
        switch(type) {
            case "Shoes":
                break;
            case "Sandals":
                comfort = 6;
                warmth = 1;
                formality = 0;
                break;
            case "Sneakers":
                comfort = 7;
                formality = 2;
                break;
            case "Formal":
                comfort = 2;
                formality = 8;
                break;
            case "Boots":
                comfort = 6;
                warmth = 8;
                break;
            default:
                throw new IllegalArgumentException("This type is not defined in Feet class");
        }
    }
}
