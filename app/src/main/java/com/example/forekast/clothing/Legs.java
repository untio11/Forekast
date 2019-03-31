package com.example.forekast.clothing;

public class Legs extends Clothing {

    @Override
    public void preSet() {
        // Get presettings when adding Legs clothing
        // Default value is 5
        switch(type) {
            case "Jeans":
                comfort = 2;
                warmth = 7;
                formality = 6;
                break;
            case "Shorts":
                warmth = 2;
                formality = 0;
                break;
            case "Skirt":
                comfort = 4;
                warmth = 0;
                formality = 7;
                break;
            case "Trousers":
                warmth = 7;
                formality = 8;
                break;
            default:
                throw new IllegalArgumentException("This type is not defined in Legs class");
        }
    }
}
