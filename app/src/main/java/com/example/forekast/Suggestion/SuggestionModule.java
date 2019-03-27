package com.example.forekast.Suggestion;

import com.example.forekast.Outfits.Outfit;
import com.example.forekast.Outfits.OutfitPowersetInterface;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.clothing.ClothingCriteriaInterface;
import com.example.forekast.external_data.Weather;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuggestionModule extends SuggestionModuleInterface {

    private ClothingCriteria criteria;
    private Weather weather;
    public SuggestionModule() {
        outfits = new OutfitPowerset();
    }

    /** Slider Criteria */
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> warmth;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> formality;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> comfort;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> preference;
    private String owner;

    /** Weather Criteria */
    private float temp;
    private float uv_index;
    private float precipitation;
    // float weather_icon; Not relevant
    private float feels_like;
    private float wind;
    // String city; Not relevant

    /** Accessories */
    private Boolean coat;
    private Boolean gloves;
    private Boolean umbrella;
    private Boolean sunglasses;
    private Boolean leggings;

    /** Clothing */
    private Clothing currentInnerTorso;
    private Clothing currentOuterTorso;
    private Clothing currentBottoms;
    private Clothing currentShoes;


    /** First establish the criteria */
    @Override
    private void setCurrentCriteria(ClothingCriteria criteria, Weather weather) {
        this.criteria = criteria;
        this.weather = weather;

        /* Slider Criteria */
        warmth = criteria.warmth;
        formality = criteria.formality;
        comfort = criteria.comfort;
        preference = criteria.preference;
        owner = criteria.owner;

        /* Weather Criteria */
        temp = weather.temp;
        uv_index = weather.uv_index;
        precipitation = weather.precipitation;
        feels_like = weather.feels_like;
        wind = weather.wind;
    }

    @Override
    private ClothingCriteria generateCriteria() {
        setCurrentCriteria(criteria, weather);
        ClothingCriteria currentCriteria = criteria;
        return currentCriteria;
    }

    // Set the booleans for the accessories based on the critieria assigned
    private void setAccessories() {
        coat = false;
        gloves = false;
        umbrella = false;
        sunglasses = false;
        leggings = false;

        /** When to suggest sunglasses */
        // If the UV Index is greater than 3 (medium risk) then wear sunglasses
        if (uv_index >= 3){
            sunglasses = true;
        }

        /** When to suggest umbrella vs a coat */
        // If there is -any- rain and the wind is calm enough
        if (precipitation > 0 && wind < 25) {
            umbrella = true;
        }
        else if (wind > 25) { // If it's too windy for an umbrella
            coat = true;
        }

        /** When to suggest coat */
        // If it's cold outside
        if (feels_like < 12) {
            coat = true;
        }
        // If it's windy
        else if (wind > 10 && feels_like < 15){
            coat = true;
        }

        /** When to suggest gloves & scarf */
        // if it feels cold
        if (feels_like < 10) {
            gloves = true;
        }

        /** When to suggest leggings */
        // If the clothes need to be warmer than 5 and the bottoms are a skirt or a dress
        if (warmth.second > 5 && (currentBottoms.type.equals("skirt") || currentInnerTorso.type.equals("dress"))){ // placeholder terms used
            leggings = true;
        }

    }

    /** Create the set of available clothing to draw from */
    @Override
    public List<Clothing> getClothing(String location) {
        List<Clothing> currentLocationList = new ArrayList<>();

        Clothing clothingInRepo = new Clothing("torso"); // Placeholder for accessing Repo

        // For all clothing in repo:
        for (int i = 0; i < 20; i++) { // Arbitrary 20 given in stead of database size
            if (!clothingInRepo.washing_machine && clothingInRepo.location.equals(location)) { // For now, only consider available clothes
                currentLocationList.add(clothingInRepo);
            }
        }

        return currentLocationList;
    }


    /** Local clothing Powerset from which the other classes derive outfits*/
    // OutfitPowerset contains lists of appropriate clothing
    @Override
    public void generateOutfit(ClothingCriteria criteria) {
        generateCriteria();
        List<List<Clothing>> localPowerset = new ArrayList<>();
        List<Clothing> inner_torso = new ArrayList<>();
        List<Clothing> outer_torso = new ArrayList<>();
        List<Clothing> bottoms = new ArrayList<>();
        List<Clothing> shoes = new ArrayList<>();

        Iterator<List<Clothing>> itr = OutfitPowersetInterface.iterator();
        Clothing clothing = new Clothing(); // Placeholder clothing item

        int warmthUpper = warmth.second; // upper bound
        int warmthLower = warmth.second; // lower bound

        int comfortUpper = comfort.second;
        int comfortLower = comfort.second;

        int formalityUpper = formality.second;
        int formalityLower = formality.second;

        int currentPreference = 10;

        while (inner_torso.isEmpty()){
            if (clothing.location.equals("torso") && clothing.underwearable == true && clothing.preference == currentPreference
                    && clothing.warmth <= warmthUpper && clothing.warmth >= warmthLower
                    && clothing.comfort <= comfortUpper && clothing.comfort >= comfortLower
                    && clothing.formality <= formalityUpper && clothing.formality >= formalityLower) {
            }
            // Expand the range
            warmthUpper++;
            warmthLower--; 

            comfortUpper++;
            comfortLower--;

            formalityUpper++;
            formalityLower--;

            currentPreference --;
        }

        // Communicate with OutfitPowersetInterface

    }

    /** Draw from the local powerset */
    // Create a random outfit based on the local powerset from generate outfit
    @Override
    public Outfit getRandomOutfit() {
        ClothingCriteria criteria;
        Outfit randomOutfit;
        String location;

        criteria = new ClothingCriteria(warmth, formality, comfort, preference, owner);

        generateOutfit(criteria);
        setAccessories();

        Clothing clothing = new Clothing(); // Placeholder clothing item

        if (clothing.location.equals("torso")) {

        }

        randomOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);

        return randomOutfit;
    }

    public void setOutfit(){
        getRandomOutfit();

    }

    /** Next & Previous button functions (connected to button in the homescreen view model */
    @Override
    public Outfit next(String location) {
        return null;
    }

    @Override
    public Outfit previous(String location) {
        return null;
    }
}
