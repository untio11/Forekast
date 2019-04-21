package com.example.forekast.Suggestion;

import android.os.AsyncTask;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.Clothing.TorsoClothing;
import com.example.forekast.ExternalData.Repository;
import com.example.forekast.ExternalData.Weather;
import com.example.forekast.HomeScreen.HomeScreen;

import java.util.ArrayList;
import java.util.List;

public class SuggestionModule extends SuggestionModuleInterface {

    /**
     * Slider Criteria (Warmth, Comfort & Formality)
     **/
    private ClothingCriteria criteria;
    private Weather weather;

    private int originalWarmth;
    /**
     * Accessories
     */
    private boolean[] accessories = new boolean[5];
    private static final int SUNGLASSES = 0;
    private static final int COAT = 1;
    private static final int GLOVES = 2;
    private static final int UMBRELLA = 3;
    private static final int LEGGINGS= 4;

    /**
     * Clothing
     */
    private TorsoClothing currentTorso;
    private Clothing currentBottoms;
    private Clothing currentShoes;
    private Outfit outfit;

    private List<TorsoClothing> torsos;
    private List<Clothing> inner_torso;
    private List<Clothing> outer_torso;
    private List<Clothing> bottoms;
    private List<Clothing> shoes;
    private int asyncCounter;
    /**
     * Outfit Powerset
     */
    private OutfitPowerset outfits = new OutfitPowerset();
    /**
     * Other
     */
    private static int currentIndT = 0;
    private static int currentIndB = 0;
    private static int currentIndS = 0;

    @Override
    public void setCurrentCriteria(ClothingCriteria criteria, Weather weather) {
        this.weather = weather;
        this.criteria = criteria.copy();

        // Temperature ratio - the warmth criteria should be an average between the current temperature and the warmth slider
        int tempRatio = (int) weather.temp / 3;

        // Setting warmth to include a subjective ratio of temperature in the suggestion
        this.criteria.warmth.setBoth(
                tempRatio + criteria.warmth.first / 2,
                tempRatio + criteria.warmth.second / 2
        );

        originalWarmth = criteria.warmth.first;

        // Produce clothing for the outfit
        System.out.println("Was this printed after changing sliders?");
        generateOutfit();
    }

    // Set the booleans for the accessories based on the criteria assigned
    public void setAccessories() {
        for (int i = 0; i < accessories.length; i++) {
            accessories[i] = false;
        }

        /* When to suggest sunglasses */
        // If the UV Index is greater than 2 (low-medium risk), then wear sunglasses
        accessories[SUNGLASSES] = weather.uv_index > 2;

        /* When to suggest umbrella vs a coat */
        // If there is some amount rain more than just a drizzle, and the wind is calm enough
        accessories[UMBRELLA] = weather.precipitation > 1 && weather.wind < 25;
        // Suggest coat when it is raining and too windy, or when it is too cold and windy.
        accessories[COAT] =
                (weather.precipitation > 1 && weather.wind >= 25) ||
                (weather.feels_like < 12) ||
                (weather.wind > 10 && weather.feels_like < 15);

        /* When to suggest gloves & scarf */
        // if it feels cold
        accessories[GLOVES] = weather.feels_like <= 0;

        /* When to suggest leggings */
        // If the clothes need to be warmer than 5 and the bottoms are a skirt or a dress
        if (criteria.warmth.second > 5) {

            if (currentBottoms != null) {
                accessories[LEGGINGS] = currentBottoms.type.equals("Skirt");
            } else if (currentTorso.torso != null) {
                accessories[LEGGINGS] = currentTorso.torso.type.equals("Dress");
            } else if (currentTorso.inner != null) {
                accessories[LEGGINGS] = currentTorso.inner.type.equals("Dress");
            }
        }
    }

    public boolean[] getAccessories() {
        return accessories;
    }

    /**
     * Local clothing Powerset from which the other classes derive outfits
     */
    // OutfitPowerset contains lists of appropriate clothing
    @Override
    void generateOutfit() {
        inner_torso = new ArrayList<>();
        outer_torso = new ArrayList<>();
        bottoms = new ArrayList<>();
        shoes = new ArrayList<>();

        System.out.println("Should be empty: " + inner_torso);
        System.out.println("Should be empty: " + outer_torso);
        System.out.println("Should be empty: " + bottoms);
        System.out.println("Should be empty: " + shoes);

        asyncCounter = 0;

        new AgentAsyncTask("innerTorso", criteria).execute();
        new AgentAsyncTask("outerTorso", criteria).execute();
        new AgentAsyncTask("Legs", criteria).execute();
        new AgentAsyncTask("Feet", criteria).execute();

        System.out.println("If yes, then the sliders caused new clothing tasks to execute");
    }

    private void setTorso() {
        System.out.println("innertorso set: " + outfits.inner_torso);
        System.out.println("outertorso set: " + outfits.outer_torso);

        torsos = new ArrayList<>();

        /** Inner Torso Single Items */
        // Create Torso object from inner torso clothing and add to the torso list
        for (Clothing clothing : outfits.inner_torso) {
            TorsoClothing newTorso = new TorsoClothing(clothing);
            if (!(torsos.contains(newTorso)) && newTorso.torso.warmth >= originalWarmth) {
                // What did you just add?
                System.out.print("adding inner item: ");
                System.out.println(newTorso.torso);
                torsos.add(newTorso);
            }
        }

        /*
        // List all the individual items inside torsos
        System.out.println("torsos inner set: ");
        for (int i = 0; i < torsos.size(); i++) {
            System.out.println(torsos.get(i).torso);
            System.out.print(torsos.get(i).inner);
            System.out.println(torsos.get(i).outer);
        }
        */

        /** Outer Torso Single Items */
        // Create Torso object from outer torso clothing and add to the torso list
        for (Clothing clothing : outfits.outer_torso){
            TorsoClothing newTorso = new TorsoClothing(clothing);
            /* Ensure that the item has not already been added to the list
            * it is not a jacket,
            * and it is warm enough on its own
            */
            if (!(torsos.contains(newTorso)) && !(newTorso.torso.type.equals("Jacket")) && (newTorso.torso.warmth >= originalWarmth)) {
                System.out.println("adding outer item: " + newTorso.torso);
                torsos.add(newTorso);
            }
        }

        /*
        System.out.println("torsos outer + inner singles set: ");
        for (int i = 0; i < torsos.size(); i++) {
            if (torsos.get(i).one && !(torsos.get(i).two)) {
                System.out.println(torsos.get(i).torso);
            } else {
                System.out.print(torsos.get(i).inner);
                System.out.println(torsos.get(i).outer);
            }
        }
        */

        /** Torso Paired Items */
        // Create Torso object from both inner and outer torso clothing and add to the torso list
        for (int i = 0; i < outfits.inner_torso.size(); i++){
            for (int j = 0; j < outfits.outer_torso.size(); j++) {
                TorsoClothing newTorso = new TorsoClothing(outfits.inner_torso.get(i), outfits.outer_torso.get(j));
                /*
                * The two items must be warm enough together
                * The inner item must not be the same type as the outer item (i.e., no Shirt on top of a Shirt)
                * If the inner item is a dress, don't suggest an outer item that is a Shirt or a Sweater
                */
                if (((newTorso.inner.warmth + newTorso.outer.warmth) / 2 >= criteria.warmth.first)
                        && (!newTorso.inner.type.equals(newTorso.outer.type))
                        && !(newTorso.inner.type.equals("Dress") && ( newTorso.outer.type.equals("Shirt") ||  newTorso.outer.type.equals("Sweater")))) {

                    System.out.println ("adding both item: " + newTorso.inner + ", " + newTorso.outer);
                    torsos.add(newTorso);
                }
            }
        }
        System.out.println("inner torso size: " + outfits.inner_torso.size() + ", outer torso size: " + outfits.outer_torso.size() + ", total torsos size: " + torsos.size());
    }

    /**
     * Draw from the local powerset and the new torsos list
     */
    // Set clothing from the powerset
    @Override
    public Outfit setOutfit() {
        setTorso(); // Special suggestion system for inner & outer torso

        System.out.println("torsos set: ");
        for (int i = 0; i < torsos.size(); i++) {
            if (torsos.get(i).one && !(torsos.get(i).two)) {
                System.out.println(torsos.get(i).torso);
            } else {
                System.out.print(torsos.get(i).inner);
                System.out.println(torsos.get(i).outer);
            }
        }

        if (torsos.size() > 0) {
            currentTorso = torsos.get(0);
        }
        if (outfits.bottoms.size() > 0) {
            currentBottoms = outfits.bottoms.get(0);
        }
        if (outfits.shoes.size() > 0) {
            currentShoes = outfits.shoes.get(0);
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        System.out.println("The given outfit is: " + outfit.torso + " " + outfit.pants + " " + outfit.shoes);
        return outfit;
    }

    /**
     * Next & Previous button functions (connected to button in the homescreen view model)
     */
    @Override
    public Outfit next(String location) {
        indexCalculator(location, 1); // Increment the index of a clothing item by 1
        updateClothes(location); // Go to the next item of the given clothing location
        return outfit;
    }

    @Override
    public Outfit previous(String location) {
        indexCalculator(location, -1); // Decrement the index of a clothing item by 1
        updateClothes(location); // Go to the previous item of the given clothing location
        return outfit;
    }

    /**
     * Refresh button function (connected to button in the homescreen view model)
     */
    @Override
    public Outfit refresh() {
        // Increment each of the clothing indices by 1
        indexCalculator("Torso", 1);
        indexCalculator("Legs", 1);
        indexCalculator("Feet", 1);

        // Go to the next item for each of the clothing locations
        updateClothes("Torso");
        updateClothes("Legs");
        updateClothes("Feet");

        return outfit;
    }

    private void indexCalculator(String location, int x) {
        switch (location) {
            case "Torso":
                currentIndT = currentIndT + x;
                break;
            case "Legs":
                currentIndB = currentIndB + x;
                break;
            case "Feet":
                currentIndS = currentIndS + x;
                break;
        }
    }

    public void updateClothes(String location){
        // Torso
        if (location.equals("Torso") && torsos != null) {
            if (torsos.size() > 0) {
                // If the index has exceeded the size of the list (gone all the way to the end)
                if (currentIndT >= torsos.size()) {
                    currentIndT = 0; // Then set the index to the beginning of the list
                }
                // If the index has been decremented below 0 (gone all the way to the start)
                else if (currentIndT < 0) {
                    currentIndT = torsos.size() - 1; // Then set the index to the end of the list
                }
                currentTorso = torsos.get(currentIndT);
            }
        }

        // Bottoms
        if (location.equals("Legs") && outfits.bottoms != null) {
            if (outfits.bottoms.size() > 0) {
                if (currentIndB >= outfits.bottoms.size()) {
                    currentIndB = 0;
                }
                else if (currentIndB < 0) {
                    currentIndB = outfits.bottoms.size() - 1;
                }
                currentBottoms = outfits.bottoms.get(currentIndB);
            }
        }

        // Shoes
        if (location.equals("Feet") && outfits.shoes != null) {
            if (outfits.shoes.size() > 0) {
                if (currentIndS >= outfits.shoes.size()) {
                    currentIndS = 0;
                }
                else if (currentIndS < 0) {
                    currentIndS = outfits.shoes.size() - 1;
                }
                currentShoes = outfits.shoes.get(currentIndS);
            }
        }

        // Set the new outfit according to whatever changes were made
        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
    }

    private class AgentAsyncTask extends AsyncTask<Void, Void, Void> {
        private final List<Clothing> clothingList;
        private final String location;
        private ClothingCriteria criteria;
        private String repoLocation;
        private int i = 0;

        AgentAsyncTask(String location, ClothingCriteria criteria) {
            clothingList = new ArrayList<>();
            this.location = location;
            this.criteria = criteria;

            repoLocation = location;
            if (repoLocation.equals("innerTorso") || repoLocation.equals("outerTorso")) {
                repoLocation = "Torso";
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            System.out.println("Iteration!" + i++);
            List<Clothing> repo = Repository.getClothing(repoLocation, criteria);
            if (repo.size() > 0) {
                for (Clothing clothing : repo) {
                    boolean add = true;
                    for (Clothing savedClothing : clothingList) {
                        if (savedClothing.ID == clothing.ID) {
                            add = false;
                        }
                    }
                    if (add) {
                        if ((!(location.equals("innerTorso")) || clothing.underwearable) &&
                                (!(location.equals("outerTorso")) || clothing.overwearable)) {
                            clothingList.add(clothing);
                        }
                    }
                }
            }

            System.out.println("After appending:" + clothingList);
            if (clothingList.size() > 10 || criteria.preference.first < 0) {
                switch (location) {
                    case "innerTorso":
                        System.out.println("Inner Torso:" + clothingList);
                        inner_torso = clothingList;
                        break;
                    case "outerTorso":
                        System.out.println("Outer Torso:" + clothingList);
                        outer_torso = clothingList;
                        break;
                    case "Legs":
                        System.out.println("Legs:" + clothingList);
                        bottoms = clothingList;
                        break;
                    case "Feet":
                        System.out.println("Feet:" + clothingList);
                        shoes = clothingList;
                        break;
                }
            } else {
                expandRange(criteria);
                doInBackground(voids);
            }

            // Communicate with OutfitPowerset
            outfits.inner_torso = inner_torso;
            outfits.outer_torso = outer_torso;
            outfits.bottoms = bottoms;
            outfits.shoes = shoes;

            return null;
        }

        private void expandRange(ClothingCriteria criteria) {
            criteria.warmth.first--;
            criteria.warmth.second++;
            criteria.formality.first--;
            criteria.formality.second++;
            criteria.comfort.first--;
            criteria.comfort.second++;
            criteria.preference.first--;
            // Upper bound of preference does not have to increase since it is always max.
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            asyncCounter++;
            if (asyncCounter == 4) {
                System.out.println("outfits inner:" + outfits.inner_torso);
                System.out.println("outfits outer:" + outfits.outer_torso);
                System.out.println("outfits bottoms:" + outfits.bottoms);
                System.out.println("outfits shoes:" + outfits.shoes);
                HomeScreen.newOutfit();
            }
        }

    }
}
