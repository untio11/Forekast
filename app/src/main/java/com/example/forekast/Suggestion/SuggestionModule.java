package com.example.forekast.Suggestion;

import android.os.AsyncTask;

import com.example.forekast.clothing.*;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.example.forekast.clothing.TorsoClothing;
import com.example.forekast.homescreen.HomeScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuggestionModule extends SuggestionModuleInterface {


    private ClothingCriteria criteria;
    private ClothingCriteria tempCriteria;
    static OutfitPowerset outfits = new OutfitPowerset();

    /** Slider Criteria */
    //private MutablePair<Integer, Integer> warmth;
    //private MutablePair<Integer, Integer> formality;
    //private MutablePair<Integer, Integer> comfort;
    //private MutablePair<Integer, Integer> preference;
    //private String owner;

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
    private TorsoClothing currentTorso;
    //private Clothing currentInnerTorso;
    //private Clothing currentOuterTorso;
    private Clothing currentBottoms;
    private Clothing currentShoes;
    private Outfit outfit;

    /** Other */
    private static int currentIndT = 0;
    //private static int currentIndOT = 0;
    //private static int currentIndIT = 0;
    private static int currentIndB = 0;
    private static int currentIndS = 0;


    /** First establish the criteria */
    @Override
    public void setCurrentCriteria(ClothingCriteria criteria, Weather weather) {
        this.weather = weather;

        /* Weather Criteria */
        this.temp = weather.temp;
        this.uv_index = weather.uv_index;
        this.precipitation = weather.precipitation;
        this.feels_like = weather.feels_like;
        this.wind = weather.wind;

        int tempRatio = (int) temp / 3;
        criteria.warmth.second = (tempRatio + criteria.warmth.second) / 2;

        this.criteria = criteria;
        this.tempCriteria = criteria;

        /* Slider Criteria */
        this.warmth = criteria.warmth;
        //this.formality = criteria.formality;
        //this.comfort = criteria.comfort;
        //this.preference = criteria.preference;
        //this.owner = criteria.owner;

        //System.out.println(warmth.second);
        warmth = new MutablePair<>((tempRatio + warmth.first) / 2, (tempRatio + warmth.second) / 2);
        //System.out.println(temp);
        //System.out.println(tempRatio);

        generateOutfit();
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
        if (uv_index >= 2){
            sunglasses = true;
        }

        /** When to suggest umbrella vs a coat */
        // If there is -any- rain and the wind is calm enough
        if (precipitation > 5 && wind < 25) {
            umbrella = true;
        }
        else if (wind >= 25) { // If it's too windy for an umbrella
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
        if (feels_like <= 0) {
            gloves = true;
        }

        /** When to suggest leggings */
        // If the clothes need to be warmer than 5 and the bottoms are a skirt or a dress
        if (warmth.second > 5){
            if (currentBottoms.type.equals("Skirt")) {
                leggings = true;
            }
            else if (currentTorso.torso != null && currentTorso.torso.type.equals("Dress")){
                leggings = true;
            }
            else {
                if (currentTorso.inner.type.equals("Dress")){
                    leggings = true;
                }
            }
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

    List<TorsoClothing> torsos = new ArrayList<>();
    //List<List<Clothing>> torsos = new ArrayList<>();

    private List<Clothing> inner_torso;
    private List<Clothing> outer_torso;
    private List<Clothing> bottoms;
    private List<Clothing> shoes;
    private int asyncCounter;

    /** Local clothing Powerset from which the other classes derive outfits*/
    // OutfitPowerset contains lists of appropriate clothing
    @Override
    public void generateOutfit() {
        inner_torso = new ArrayList<>();
        outer_torso = new ArrayList<>();
        bottoms = new ArrayList<>();
        shoes = new ArrayList<>();
        asyncCounter = 0;

        new AgentAsyncTask("innerTorso", criteria).execute();
        new AgentAsyncTask("outerTorso", criteria).execute();
        new AgentAsyncTask("Legs", criteria).execute();
        new AgentAsyncTask("Feet", criteria).execute();
    }

    public void setTorso(){
        System.out.println("innertorso set: "+outfits.inner_torso);
        System.out.println("outertorso set: "+outfits.outer_torso);

        //List<Clothing> innies = new ArrayList<>();
        //List<Clothing> outies = new ArrayList<>();
        //List<Clothing> bothies = new ArrayList<>();

        // Create Torso object from inner torso clothing and add to the torso list

        for (Clothing clothing : outfits.inner_torso){
            System.out.println("adding inner item");
            TorsoClothing newTorso = new TorsoClothing(clothing);
            System.out.println(newTorso.torso);
            torsos.add(new TorsoClothing(clothing));
        }

        // Create Torso object from outer torso clothing and add to the torso list
        for (Clothing clothing : outfits.outer_torso){
            System.out.println("adding outer item");
            torsos.add(new TorsoClothing(clothing));
        }

        // Create Torso object from both inner and outer torso clothing and add to the torso list
        for (int i = 0; i < outfits.inner_torso.size(); i++){
            for (int j = 0; j < outfits.outer_torso.size(); j++) {
                if ((outfits.inner_torso.get(i).warmth + outfits.outer_torso.get(j).warmth) / 2 >=
                        criteria.warmth.first){ // Use original criteria to fit the slider
                    torsos.add(new TorsoClothing (outfits.inner_torso.get(i), outfits.outer_torso.get(j)));
                    System.out.println("adding both item");
                }
            }
        }

        System.out.println(outfits.inner_torso.size());
        System.out.println(outfits.outer_torso.size());
        System.out.println(torsos.size());

        System.out.println(torsos.get(0).torso);
        System.out.println(torsos.get(0).inner);
        System.out.println(torsos.get(0).outer);
    }
    /** Draw from the local powerset */
    // Set clothing from the powerset
    @Override
    public Outfit setOutfit(){

        //System.out.println("innertorso set: "+outfits.inner_torso);
        //System.out.println("outertorso set: "+outfits.outer_torso);

        // Need to get the inner_torso and outer_torso values to relate somewhere
        setTorso();

        System.out.println("torsos set: "+torsos);

        if (torsos.size() > 0){
            currentTorso = torsos.get(0);
        }
        if (outfits.bottoms.size() > 0) {
            currentBottoms = outfits.bottoms.get(0);
        }
        if (outfits.shoes.size() > 0){
            currentShoes = outfits.shoes.get(0);
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        return outfit;
    }

    /** Next & Previous button functions (connected to button in the homescreen view model */
    @Override
    public Outfit next(String location) {
        indexCalculator(location, 1); // Increment the index of a clothing item by 1

        if (location.equals("Torso") && torsos.size() > 0){
            // Inner Torso
            if (currentIndT >= torsos.size()){
                currentIndT = 0;
            }
            currentTorso = torsos.get(currentIndT);
        }

        // Bottoms
        else if (location.equals("Legs") && outfits.bottoms.size() > 0){
            if (currentIndB >= outfits.bottoms.size()){
                currentIndB = 0;
            }
            currentBottoms = outfits.bottoms.get(currentIndB);
        }

        // Shoes
        else if (location.equals("Feet") && outfits.shoes.size() > 0){
            if (currentIndS >= outfits.shoes.size()){
                currentIndS = 0;
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        return outfit;
    }

    @Override
    public Outfit previous(String location) {
        indexCalculator(location, -1); // Decrement the index of a clothing item by 1

        //Torso
        if (location.equals("Torso") && torsos.size() > 0){
            // Inner
            if (currentIndT < 0){ // If the index has been decremented below 0 (gone all the way to the start)
                currentIndT = torsos.size() - 1; // then set the index to the end of the list
            }
            currentTorso = torsos.get(currentIndT);
        }
        // Bottoms
        else if (location.equals("Legs") && outfits.bottoms.size() > 0){
            if (currentIndB < 0){
                currentIndB = outfits.bottoms.size() - 1;
            }
            currentBottoms = outfits.inner_torso.get(currentIndB);
        }
        // Shoes
        else if (location.equals("Feet") && outfits.shoes.size() > 0){
            if (currentIndS < 0){
                currentIndS = outfits.shoes.size() - 1;
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        return outfit;
    }

    /** Refresh button function (connected to button in the homescreen view model */
    @Override
    public Outfit refresh(){
        /** Increment each of the clothing items by 1*/
        indexCalculator("Torso", 1);
        indexCalculator("Legs", 1);
        indexCalculator("Feet", 1);

        if (torsos.size() > 0){
            // Inner Torso
            if (currentIndT >= torsos.size()){
                currentIndT = 0;
            }
            currentTorso = torsos.get(currentIndT);
        }

        // Bottoms
        if (outfits.bottoms.size() > 0){
            if (currentIndB >= outfits.bottoms.size()){
                currentIndB = 0;
            }
            currentBottoms = outfits.bottoms.get(currentIndB);
        }

        // Shoes
        if (outfits.shoes.size() > 0){
            if (currentIndS >= outfits.shoes.size()){
                currentIndS = 0;
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        System.out.println(outfit);
        System.out.println(currentTorso);
        System.out.println(currentBottoms);
        System.out.println(currentShoes);
        return outfit;
    }

    private void indexCalculator(String location, int x){
        if (location.equals("Torso")){
            currentIndT = currentIndT + x;
        }
        else if (location.equals("Legs")) {
            currentIndB = currentIndB + x;
        }
        else if (location.equals("Feet")) {
            currentIndS = currentIndS + x;
        }
    }

    private class AgentAsyncTask extends AsyncTask<Void, Void, Void> {
        List<Clothing> clothingList;
        private String location;
        private String repoLocation;
        private ClothingCriteria criteria;

        AgentAsyncTask(String location, ClothingCriteria criteria) {
            clothingList = new ArrayList<>();
            this.location = location;
            this.repoLocation = location;
            if (repoLocation == "innerTorso" || repoLocation == "outerTorso") {
                repoLocation = "Torso";
            }
            this.criteria = criteria;
        }

        @Override
        protected Void doInBackground(Void ... voids) {
            System.out.println("Iteration!");
            List<Clothing> repo = Repository.getClothing(repoLocation, criteria);
            if (repo.size() > 0) {
                for (Clothing clothing : repo) {
                    if (!clothingList.contains(clothing)) {
                        if ((location != "innerTorso" || clothing.underwearable) &&
                                (location != "outerTorso" || clothing.overwearable)) {
                            clothingList.add(clothing);
                        }
                    }
                }
            }
            if (clothingList.size() > 5 || criteria.preference.first < 0) {
                switch (location) {
                    case "innerTorso":
                        inner_torso = clothingList;
                        break;
                    case "outerTorso":
                        outer_torso = clothingList;
                        break;
                    case "Legs":
                        bottoms = clothingList;
                        break;
                    case "Feet":
                        shoes = clothingList;
                        break;
                }
                return null;
            } else {
                criteria.expandRange();
                doInBackground(voids);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            asyncCounter++;
            if (asyncCounter == 4) {

                // Communicate with OutfitPowerset
                outfits.inner_torso = inner_torso;
                outfits.outer_torso = outer_torso;
                outfits.bottoms = bottoms;
                outfits.shoes = shoes;
                outfits.set();
                HomeScreen.newOutfit();
            }
        }
    }
}
