package com.example.forekast.Suggestion;

import android.util.Log;

import com.example.forekast.clothing.*;
import com.example.forekast.clothing.ClothingCriteriaInterface;
import com.example.forekast.clothing.ClothingCriteriaInterface.MutablePair;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuggestionModule extends SuggestionModuleInterface {

    private ClothingCriteria criteria;
    private ClothingCriteria tempCriteria;
    private Weather weather;
    static OutfitPowerset outfits = new OutfitPowerset();

    /** Slider Criteria */
    private MutablePair<Integer, Integer> warmth;
    private MutablePair<Integer, Integer> formality;
    private MutablePair<Integer, Integer> comfort;
    private MutablePair<Integer, Integer> preference;
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
    private boolean coat = false;
    private boolean gloves = false;
    private boolean umbrella = false;
    private boolean sunglasses = false;
    private boolean leggings = false;

    /** Clothing */
    private Clothing currentInnerTorso;
    private Clothing currentOuterTorso;
    private Clothing currentBottoms;
    private Clothing currentShoes;

    /** Other */
    private static int currentIndOT;
    private static int currentIndIT;
    private static int currentIndB;
    private static int currentIndS;

    /** First establish the criteria */
    @Override
    public void setCurrentCriteria(ClothingCriteria criteria, Weather weather) {
        this.criteria = criteria;
        this.tempCriteria = criteria;
        this.weather = weather;

        /* Slider Criteria */
        this.warmth = criteria.warmth;
        this.formality = criteria.formality;
        this.comfort = criteria.comfort;
        this.preference = criteria.preference;
        this.owner = criteria.owner;

        /* Weather Criteria */
        this.temp = weather.temp;
        this.uv_index = weather.uv_index;
        this.precipitation = weather.precipitation;
        this.feels_like = weather.feels_like;
        this.wind = weather.wind;

        int tempRatio =  (int) temp / 3;
        System.out.println(warmth.second);
        warmth.second = (tempRatio + warmth.second) / 2;
        System.out.println(temp);
        System.out.println(tempRatio);

        generateOutfit();
    }

    // Set the booleans for the accessories based on the critieria assigned
    public void setAccessories() {
        if (weather == null) {
            return;
        }

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
        if (feels_like < 3) {
            gloves = true;
        }

        /** When to suggest leggings */
        // If the clothes need to be warmer than 5 and the bottoms are a skirt or a dress
        if (warmth.second > 5 && (currentBottoms.type.equals("Skirt") || currentInnerTorso.type.equals("Dress"))){
            leggings = true;
        }

    }

    public boolean[] getAccessories(){
        boolean[] accessories = {false, false, false, false, false};
        if (criteria != null){
            accessories = new boolean[] {sunglasses, coat, gloves, umbrella, leggings};
        }
        return accessories;
    }

    List<Clothing> inner_torso = new ArrayList<>();
    List<Clothing> outer_torso = new ArrayList<>();
    List<Clothing> bottoms = new ArrayList<>();
    List<Clothing> shoes = new ArrayList<>();

    private void resetRange() {
        tempCriteria = criteria;
    }

    /** Local clothing Powerset from which the other classes derive outfits*/
    // OutfitPowerset contains lists of appropriate clothing
    @Override
    public void generateOutfit() {
        generateITRecursion();
        generateOTRecursion();
        generateBRecursion();
        generateSRecursion();

        // Communicate with OutfitPowerset
        outfits.inner_torso = inner_torso;
        outfits.outer_torso = outer_torso;
        outfits.bottoms = bottoms;
        outfits.shoes = shoes;
        outfits.set();
    }

    private void generateITRecursion() {
        List<Clothing> repoIT = Repository.getClothing("Torso", tempCriteria);
        for (Clothing clothing : repoIT) {
            if (clothing.underwearable && !inner_torso.contains(clothing)) {
                inner_torso.add(clothing);
            }
        }
        if (inner_torso.size() < 5 && tempCriteria.preference.second != 0) {
            tempCriteria.expandRange();
            generateITRecursion();
        } else {
            resetRange();
        }
    }

    private void generateOTRecursion() {
        List<Clothing> repoOT = Repository.getClothing("Torso", tempCriteria);
        for (Clothing clothing : repoOT) {
            if (clothing.overwearable && !outer_torso.contains(clothing)) {
                outer_torso.add(clothing);
            }
        }
        if (outer_torso.size() < 5 && tempCriteria.preference.second != 0) {
            tempCriteria.expandRange();
            generateOTRecursion();
        } else {
            resetRange();
        }
    }

    private void generateBRecursion() {
        List<Clothing> repoB = Repository.getClothing("Legs", tempCriteria);
        System.out.println("Based on criteria: " + tempCriteria);
        System.out.println(repoB);
        for (Clothing clothing : repoB) {
            if (!bottoms.contains(clothing)) {
                bottoms.add(clothing);
            }
        }
        if (bottoms.size() < 5 && tempCriteria.preference.second != 0) {
            tempCriteria.expandRange();
            generateBRecursion();
        } else {
            resetRange();
        }
    }

    private void generateSRecursion() {
        List<Clothing> repoS = Repository.getClothing("Feet", tempCriteria);
        for (Clothing clothing : repoS) {
            if (!shoes.contains(clothing)) {
                shoes.add(clothing);
            }
        }
        if (shoes.size() < 5 && tempCriteria.preference.second != 0) {
            tempCriteria.expandRange();
            generateSRecursion();
        } else {
            resetRange();
        }
    }

    /** Draw from the local powerset */
    // Create a random outfit based on the local powerset from generate outfit
    @Override
    public Outfit getRandomOutfit() {
        ClothingCriteria criteria = new ClothingCriteria(warmth, formality, comfort, preference, owner);

        setClothing();

        Outfit randomOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);
        return randomOutfit;
    }

    private void setClothing(){
        Clothing tempInner = null;
        Clothing tempOuter = null;

        int index = 0;

        System.out.println("innertorso set: " + outfits.inner_torso);

        /*
        // Needs optimising
        if (outfits.inner_torso.size() > 0 || outfits.outer_torso.size() > 0) {
            while ((tempInner == null || tempOuter == null) && (index < outfits.outer_torso.size() || index < outfits.inner_torso.size())) {

                if (outfits.inner_torso.size() > 0 && tempInner == null) {
                    tempInner = outfits.inner_torso.get(index);
                    currentIndIT = index;
                }
                if (outfits.outer_torso.size() > 0 && tempOuter == null) {
                    tempOuter = outfits.outer_torso.get(index);
                    currentIndOT = index;
                }
                index++;
            }
            if (outfits.inner_torso.size() > 0 && outfits.outer_torso.size() > 0) {
                if ((tempInner.warmth + tempOuter.warmth) >= (warmth.second * 2)) {
                    currentInnerTorso = tempInner;
                    currentOuterTorso = tempOuter;
                }
            }

            if (tempInner == null){
                if (outfits.inner_torso.size() > 0) {
                    currentInnerTorso = outfits.inner_torso.get(0);
                    currentIndIT = 0;
                }
            }
            else if (tempOuter == null){
                if (outfits.outer_torso.size() > 0) {
                    currentOuterTorso = outfits.outer_torso.get(0);
                    currentIndOT = 0;
                }
            }
        }
        */
        if (outfits.inner_torso.size() > 0) {
            currentInnerTorso = outfits.inner_torso.get(0);
        }
        if (outfits.outer_torso.size() > 0) {
            currentOuterTorso = outfits.outer_torso.get(0);
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
            System.out.println("lists:" + category);
            Collections.shuffle(category);
        }
        setClothing();
    }

    /** Next & Previous button functions (connected to button in the homescreen view model */
    @Override
    public Outfit next(String location) {
        indexCalculator(location, 1);

        if (location.equals("Torso") && outfits.inner_torso.size() > 0){
            // Inner Torso
            if (currentIndIT > outfits.inner_torso.size()){
                currentIndIT = 0;
            }
            currentInnerTorso = outfits.inner_torso.get(currentIndIT);

            // Outer Torso
            if (currentIndOT > outfits.outer_torso.size()){
                currentIndOT = 0;
            }
            currentOuterTorso = outfits.inner_torso.get(currentIndOT);
        }

        // Bottoms
        else if (location.equals("Legs") && outfits.outer_torso.size() > 0){
            if (currentIndB > outfits.bottoms.size()){
                currentIndB = 0;
            }
            currentBottoms = outfits.inner_torso.get(currentIndB);
        }

        // Shoes
        else if (location.equals("Feet") && outfits.shoes.size() > 0){
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
        indexCalculator(location, -1);

        //Torso
        if (location.equals("Torso") && outfits.inner_torso.size() > 0){
            // Inner
            if (currentIndIT < 0){
                currentIndIT = outfits.inner_torso.size();
            }
            currentInnerTorso = outfits.inner_torso.get(currentIndIT);
            // Outer
            if (currentIndOT < 0){
                currentIndOT = outfits.outer_torso.size();
            }
            currentOuterTorso = outfits.inner_torso.get(currentIndOT);
        }
        // Bottoms
        else if (location.equals("Legs") && outfits.bottoms.size() > 0){
            if (currentIndB < 0){
                currentIndB = outfits.bottoms.size();
            }
            currentBottoms = outfits.inner_torso.get(currentIndB);
        }
        // Shoes
        else if (location.equals("Feet") && outfits.shoes.size() > 0){
            if (currentIndS < 0){
                currentIndS = outfits.shoes.size();
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }
        Outfit previousOutfit = new Outfit(currentInnerTorso, currentOuterTorso, currentBottoms, currentShoes, coat, gloves, umbrella, sunglasses, leggings);
        return previousOutfit;
    }

    private void indexCalculator(String location, int x){
        if (location.equals("Torso")){
            currentIndIT = currentIndIT + x;
            currentIndOT = currentIndOT + x;
        }
        else if (location.equals("Legs")) {
            currentIndB = currentIndB + x;
        }
        else if (location.equals("Feet")) {
            currentIndS = currentIndS + x;
        }
    }
}
