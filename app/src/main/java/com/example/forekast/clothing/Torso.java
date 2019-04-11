package com.example.forekast.clothing;

public class Torso extends Clothing {

    @Override
    public void preSet() {
        // Get presettings when adding Torso clothing
        // Default value is 5, over- and underwearable are by default false
        switch(type) {
            case "T-Shirt":
                warmth = 2;
                formality = 2;
                underwearable = true;
                break;
            case "Dress":
                comfort = 3;
                warmth = 4;
                formality = 8;
                underwearable = true;
                break;
            case "Jacket":
                comfort = 4;
                warmth = 8;
                formality = 6;
                overwearable = true;
                break;
            case "Shirt":
                warmth = 4;
                formality = 6;
                underwearable = true;
                overwearable = true;
                break;
            case "Sweater":
                comfort = 7;
                warmth = 7;
                formality = 2;
                overwearable = true;
                break;
            case "Tanktop":
                warmth = 0;
                formality = 0;
                underwearable = true;
                break;
            default:
                throw new IllegalArgumentException("This type is not defined in Torso class");
        }
    }
}