package com.example.forekast;

import android.util.Pair;

import java.util.List;

class SuggestionModule extends SuggestionModuleInterface {

    private ClothingCriteria criteria;
    private Weather weather;

    /* Slider Criteria */
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> warmth;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> formality;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> comfort;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> preference;
    private String owner;

    /* Weather Criteria */
    private int temp;
    private int uv_index;
    private int precipitation;
    // int weather_icon; Not relevant
    private int feels_like;
    private int wind;
    // String city; Not relevant

    /* Accessories */
    private Boolean coat;
    private Boolean gloves;
    private Boolean umbrella;
    private Boolean sunglasses;
    private Boolean leggings;

    /* Clothing */
    private Clothing currentInnerTorso;
    private Clothing currentOuterTorso;
    private Clothing currentBottoms;
    private Clothing currentShoes;


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
    void setAccessories() {
        coat = false;
        gloves = false;
        umbrella = false;
        sunglasses = false;
        leggings = false;

        /* When to suggest sunglasses */
        // If the UV Index is greater than 3 (medium risk) then wear sunglasses
        if (uv_index >= 3){
            sunglasses = true;
        }

        /* When to suggest umbrella vs a coat */
        // If there is more than 50% chance of rain, but
        if (precipitation > 50 && wind < 25) {
            umbrella = true;
        }
        else if (wind > 25) { // If it's too windy
            coat = true;
        }

        /* When to suggest coat */
        // If it's cold outside
        if (feels_like < 12) {
            coat = true;
        }
        // If it's windy
        else if (wind > 10 && feels_like < 15){
            coat = true;
        }

        /* When to suggest gloves & scarf */
        // if it feels cold
        if (feels_like < 10) {
            gloves = true;
        }

        /* When to suggest leggings */
        // If the clothes need to be warmer than 5 and the bottoms are a skirt or a dress
        if (warmth.second > 5 && (randomOutfit.pants == SKIRT || randomOutfit.top == DRESS)){
            leggings = true;
        }

    }

    @Override
    public List<Clothing> getClothing(String location, ClothingCriteria criteria) {
        if (location.equals("inner_torso")){

        }

        return null;
    }

    // Create local clothing Powerset from which the other classes derive outfits
    // OutfitPowerset contains lists of appropriate clothing
    @Override
    public void generateOutfit(ClothingCriteria criteria) {

        // Communicate with OutfitPowersetInterface

    }

    // Create a random outfit based on the local powerset from generate outfit
    @Override
    public Outfit getRandomOutfit() {
        ClothingCriteria criteria;
        Outfit randomOutfit;
        String location;

        criteria = new ClothingCriteria(warmth, formality, comfort, preference, owner);

        generateOutfit(criteria);
        setAccessories();

        randomOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);

        return randomOutfit;
    }

    @Override
    public Outfit next(ClothingType type) {
        return null;
    }

    @Override
    public Outfit previous(ClothingType type) {
        return null;
    }
}
