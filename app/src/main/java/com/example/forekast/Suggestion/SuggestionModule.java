package com.example.forekast.Suggestion;

import android.util.Log;

import com.example.forekast.clothing.*;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuggestionModule extends SuggestionModuleInterface {

    private ClothingCriteria criteria;
    private Weather weather;
    OutfitPowerset outfits = new OutfitPowerset();

    /** Slider Criteria */
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> warmth;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> formality;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> comfort;
    private ClothingCriteriaInterface.MutablePair<Integer, Integer> preference;
    private String owner;

    /** Weather Criteria */
    private int temp;
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

    /** Other */
    private int currentIndOT;
    private int currentIndIT;
    private int currentIndB;
    private int currentIndS;

    /** First establish the criteria */
    @Override
    public void setCurrentCriteria(ClothingCriteria criteria, Weather weather) {
        this.criteria = criteria;
        this.weather = weather;

        /* Slider Criteria */
        warmth = criteria.warmth;
        formality = criteria.formality;
        comfort = criteria.comfort;
        preference = criteria.preference;
        owner = criteria.owner;

        /* Weather Criteria */
        temp = (int) weather.temp;
        uv_index = weather.uv_index;
        precipitation = weather.precipitation;
        feels_like = weather.feels_like;
        wind = weather.wind;

        int tempRatio = temp / 3;
        warmth.second = (tempRatio + warmth.second) / 2;
    }

    // Set the booleans for the accessories based on the critieria assigned
    public void setAccessories() {
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
        if (warmth.second > 5 && (currentBottoms.type.equals("Skirt") || currentInnerTorso.type.equals("Dress"))){
            leggings = true;
        }

    }

    /** Create the set of available clothing to draw from */
    /*
    @Override
    public List<Clothing> getClothing(String location) {
        List<Clothing> currentLocationList = new ArrayList<>();

        Clothing clothingInRepo = new Torso(); // Placeholder for accessing Repo

        // For all clothing in repo:
        for (int i = 0; i < 20; i++) { // Arbitrary 20 given in stead of database size
            if (!clothingInRepo.washing_machine && clothingInRepo.location.equals(location)) { // For now, only consider available clothes
                currentLocationList.add(clothingInRepo);
            }
        }

        return currentLocationList;
    }*/


    /** Local clothing Powerset from which the other classes derive outfits*/
    // OutfitPowerset contains lists of appropriate clothing
    @Override
    public void generateOutfit(ClothingCriteria criteria) {
        setCurrentCriteria(criteria, weather);

        List<Clothing> inner_torso = new ArrayList<>();
        List<Clothing> outer_torso = new ArrayList<>();
        List<Clothing> bottoms = new ArrayList<>();
        List<Clothing> shoes = new ArrayList<>();

        List<Clothing> repoIT = new ArrayList<>();
        List<Clothing> repoOT = new ArrayList<>();
        List<Clothing> repoB = new ArrayList<>();
        List<Clothing> repoS = new ArrayList<>();

        repoIT = Repository.getClothing("Torso", criteria);
        repoOT = Repository.getClothing("Torso", criteria);
        repoB = Repository.getClothing("Legs", criteria);
        repoS = Repository.getClothing("Feet", criteria);

        //Clothing clothing = new Clothing(); // Placeholder clothing item

        int warmthUpper = warmth.second; // upper bound
        int warmthLower = warmth.second; // lower bound

        int comfortUpper = comfort.second;
        int comfortLower = comfort.second;

        int formalityUpper = formality.second;
        int formalityLower = formality.second;

        int currentPreference = 10;


        for (Clothing clothing : repoIT){
            if (clothing.location.equals("Torso") && clothing.preference == currentPreference
                    && clothing.overwearable
                    /*&& clothing.warmth <= warmthUpper && clothing.warmth >= warmthLower*/
                    && clothing.comfort <= comfortUpper && clothing.comfort >= comfortLower
                    && clothing.formality <= formalityUpper && clothing.formality >= formalityLower
                    && clothing.owner == owner) {
                inner_torso.add(clothing);
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

        for (Clothing clothing: repoOT){
            if (clothing.location.equals("Torso") && clothing.preference == currentPreference
                    && clothing.underwearable
                    /*&& clothing.warmth <= warmthUpper && clothing.warmth >= warmthLower*/
                    && clothing.comfort <= comfortUpper && clothing.comfort >= comfortLower
                    && clothing.formality <= formalityUpper && clothing.formality >= formalityLower
                    && clothing.owner == owner) {
                outer_torso.add(clothing);
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

        for (Clothing clothing: repoB){
            if (clothing.location.equals("Legs") && clothing.preference == currentPreference
                    && clothing.warmth <= warmthUpper && clothing.warmth >= warmthLower
                    && clothing.comfort <= comfortUpper && clothing.comfort >= comfortLower
                    && clothing.formality <= formalityUpper && clothing.formality >= formalityLower
                    && clothing.owner == owner) {
                bottoms.add(clothing);
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

        for (Clothing clothing : repoS){
            if (clothing.location.equals("Feet") && clothing.preference == currentPreference
                    && clothing.warmth <= warmthUpper && clothing.warmth >= warmthLower
                    && clothing.comfort <= comfortUpper && clothing.comfort >= comfortLower
                    && clothing.formality <= formalityUpper && clothing.formality >= formalityLower
                    && clothing.owner == owner) {
                shoes.add(clothing);
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

        // Communicate with OutfitPowerset
        outfits.inner_torso = inner_torso;
        outfits.outer_torso = outer_torso;
        outfits.bottoms = bottoms;
        outfits.shoes = shoes;
    }

    /** Draw from the local powerset */
    // Create a random outfit based on the local powerset from generate outfit
    @Override
    public Outfit getRandomOutfit() {
        ClothingCriteria criteria = new ClothingCriteria(warmth, formality, comfort, preference, owner);

        if (outfits == null) {
            generateOutfit(criteria);
            setAccessories();
        }

        shuffle();
        setClothing();

        Outfit randomOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);
        return randomOutfit;
    }

    private void setClothing(){
        Clothing tempInner = null;
        Clothing tempOuter = null;

        int index = 0;

        // Needs optimising
        if (outfits.inner_torso.size() > 0 && outfits.outer_torso.size() > 0) {
            while (tempInner == null && tempOuter == null && (index < outfits.outer_torso.size() || index < outfits.inner_torso.size())) {
                tempInner = outfits.inner_torso.get(index);
                currentIndIT = index;
                tempOuter = outfits.outer_torso.get(index);
                currentIndOT = index;

                if ((tempInner.warmth + tempOuter.warmth) >= (warmth.second * 2)) {
                    currentInnerTorso = tempInner;
                    currentOuterTorso = tempOuter;
                }

                index++;
            }
            if (tempInner == null){
                currentInnerTorso = outfits.inner_torso.get(0);
                currentIndIT = 0;
            }
            else if (tempOuter == null){
                currentOuterTorso = outfits.outer_torso.get(0);
                currentIndOT = 0;
            }
        }
        if (outfits.bottoms.size() > 0) {
            currentBottoms = outfits.bottoms.get(0);
        }
        if (outfits.shoes.size() > 0){
            currentShoes = outfits.shoes.get(0);
        }
    }

    private void shuffle() {
        for (List<Clothing> category : outfits) {
            Collections.shuffle(category);
        }
    }

    /** Next & Previous button functions (connected to button in the homescreen view model */
    @Override
    public Outfit next(String location) {
        if (location.equals("Torso")){
            currentIndIT++;
            if (currentIndIT > outfits.inner_torso.size()){
                currentIndIT = 0;
            }
            currentInnerTorso = outfits.inner_torso.get(currentIndIT);

            currentIndOT++;
            if (currentIndOT > outfits.outer_torso.size()){
                currentIndOT = 0;
            }
            currentOuterTorso = outfits.inner_torso.get(currentIndOT);
        }
        else if (location.equals("Legs")){
            currentIndB++;
            if (currentIndB > outfits.bottoms.size()){
                currentIndB = 0;
            }
            currentBottoms = outfits.inner_torso.get(currentIndB);
        }
        else if (location.equals("Feet")){
            currentIndS++;
            if (currentIndS > outfits.shoes.size()){
                currentIndS = 0;
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }
        Outfit nextOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);
        return nextOutfit;
    }

    @Override
    public Outfit previous(String location) {
        if (location.equals("Torso")){
            currentIndIT--;
            if (currentIndIT < 0){
                currentIndIT = outfits.inner_torso.size();
            }
            currentInnerTorso = outfits.inner_torso.get(currentIndIT);

            currentIndOT--;
            if (currentIndOT < 0){
                currentIndOT = outfits.outer_torso.size();
            }
            currentOuterTorso = outfits.inner_torso.get(currentIndOT);
        }
        else if (location.equals("Legs")){
            currentIndB--;
            if (currentIndB < 0){
                currentIndB = outfits.bottoms.size();
            }
            currentBottoms = outfits.inner_torso.get(currentIndB);
        }
        else if (location.equals("Feet")){
            currentIndS--;
            if (currentIndS < 0){
                currentIndS = outfits.shoes.size();
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }
        Outfit nextOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);
        return nextOutfit;
    }
}
