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
     * Outfit powerset
     */
    private static final OutfitPowerset outfits = new OutfitPowerset();
    /**
     * Other
     */
    private static int currentIndT = 0;
    private static int currentIndB = 0;
    private static int currentIndS = 0;
    /**
     * Slider Criteria
     **/
    private ClothingCriteria criteria;
    private float uv_index;
    // String city; Not relevant
    private float precipitation;
    // float weather_icon; Not relevant
    private float feels_like;
    private float wind;
    /**
     * Accessories
     */
    private boolean coat = false;
    private boolean gloves = false;
    private boolean umbrella = false;
    private boolean sunglasses = false;
    private boolean leggings = false;
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
     * First establish the criteria
     */
    @Override
    public void setCurrentCriteria(ClothingCriteria criteria, Weather weather) {
        this.weather = weather;

        /* Weather Criteria */
        float temp = weather.temp;
        this.uv_index = weather.uv_index;
        this.precipitation = weather.precipitation;
        this.feels_like = weather.feels_like;
        this.wind = weather.wind;

        int tempRatio = (int) temp / 3;
        criteria.warmth.second = (tempRatio + criteria.warmth.second) / 2;

        this.criteria = criteria;
        ClothingCriteria tempCriteria = criteria;

        // Setting warmth to include a subjective ratio of temperature in the suggestion
        criteria.warmth = new ClothingCriteria.MutablePair<>((tempRatio + criteria.warmth.first) / 2, (tempRatio + criteria.warmth.second) / 2);

        // Produce clothing for the outfit
        generateOutfit();
    }

    // Set the booleans for the accessories based on the critieria assigned
    public void setAccessories() {
        coat = false;
        gloves = false;
        umbrella = false;
        sunglasses = false;
        leggings = false;

        /* When to suggest sunglasses */
        // If the UV Index is greater than 2 (low-medium risk), then wear sunglasses
        if (uv_index > 2) {
            sunglasses = true;
        }

        /* When to suggest umbrella vs a coat */
        // If there is some amount rain more than just a drizzle, and the wind is calm enough
        if (precipitation > 1 && wind < 25) {
            umbrella = true;
        } else if (wind >= 25) { // If it's too windy for an umbrella
            coat = true;
        }

        /* When to suggest coat */
        // If it's cold outside
        if (feels_like < 12) {
            coat = true;
        }
        // If it's windy
        else if (wind > 10 && feels_like < 15) {
            coat = true;
        }

        /* When to suggest gloves & scarf */
        // if it feels cold
        if (feels_like <= 0) {
            gloves = true;
        }

        /* When to suggest leggings */
        // If the clothes need to be warmer than 5 and the bottoms are a skirt or a dress
        if (criteria.warmth.second > 5 && currentBottoms != null) {
            if (currentBottoms.type.equals("Skirt")) {
                leggings = true;
            } else if (currentTorso.torso != null) {
                if (currentTorso.torso.type.equals("Dress")) {
                    leggings = true;
                }
            } else if (currentTorso.inner != null) {
                if (currentTorso.inner.type.equals("Dress")) {
                    leggings = true;
                }
            }
        }
    }

    public boolean[] getAccessories() {
        boolean[] accessories = {false, false, false, false, false};
        if (criteria != null) {
            accessories = new boolean[]{sunglasses, coat, gloves, umbrella, leggings};
        }
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

        //setOutfit();
    }

    private void setTorso() {
        System.out.println("innertorso set: " + outfits.inner_torso);
        System.out.println("outertorso set: " + outfits.outer_torso);

        torsos = new ArrayList<>();

        /* Inner Torso Single Items */
        // Create Torso object from inner torso clothing and add to the torso list
        for (Clothing clothing : outfits.inner_torso) {
            if (!(torsos.contains(clothing))) {
                // What did you just add?
                System.out.print("adding inner item: ");
                TorsoClothing newTorso = new TorsoClothing(clothing);
                System.out.println(newTorso.torso);

                torsos.add(newTorso);
            }
        }

        // List all the individual items inside torsos
        System.out.println("torsos inner set: ");
        for (int i = 0; i < torsos.size(); i++) {

            System.out.println(torsos.get(i).torso);
            System.out.print(torsos.get(i).inner);
            System.out.println(torsos.get(i).outer);

        }

        /* Outer Torso Single Items */
        // Create Torso object from outer torso clothing and add to the torso list
        for (Clothing clothing : outfits.outer_torso) {
            if (!(torsos.contains(clothing)) && !(clothing.type.equals("Jacket"))) {

                // What did you just add?
                System.out.print("adding outer item: ");
                TorsoClothing newTorso = new TorsoClothing(clothing);
                System.out.println(newTorso.torso);

                torsos.add(newTorso);
            }
        }

        System.out.println("torsos outer + inner singles set: ");
        for (int i = 0; i < torsos.size(); i++) {
            if (torsos.get(i).one && !(torsos.get(i).two)) {
                System.out.println(torsos.get(i).torso);
            } else {
                System.out.print(torsos.get(i).inner);
                System.out.println(torsos.get(i).outer);
            }
        }

        /* Torso Paired Items */
        // Create Torso object from both inner and outer torso clothing and add to the torso list
        for (int i = 0; i < outfits.inner_torso.size(); i++) {
            for (int j = 0; j < outfits.outer_torso.size(); j++) {
                if (((outfits.inner_torso.get(i).warmth + outfits.outer_torso.get(j).warmth) / 2 >= criteria.warmth.first)
                        && (outfits.inner_torso.get(i) != outfits.outer_torso.get(j))
                        && !(outfits.inner_torso.get(i).type.equals("Dress") && (outfits.outer_torso.get(j).type.equals("Shirt") || outfits.outer_torso.get(j).type.equals("Sweater")))) {

                    System.out.print("adding both item: ");
                    TorsoClothing newTorso = new TorsoClothing(outfits.inner_torso.get(i), outfits.outer_torso.get(j));
                    System.out.print(newTorso.inner);
                    System.out.println(newTorso.outer);


                    torsos.add(newTorso);
                }
            }
        }

        System.out.println(outfits.inner_torso.size());
        System.out.println(outfits.outer_torso.size());
        System.out.println(torsos.size());
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
        System.out.println("The given outfit is: " + outfit);
        return outfit;
    }

    /**
     * Next & Previous button functions (connected to button in the homescreen view model
     */
    @Override
    public Outfit next(String location) {
        indexCalculator(location, 1); // Increment the index of a clothing item by 1

        if (location.equals("Torso") && torsos != null) {
            if (torsos.size() > 0) {
                // Inner Torso
                if (currentIndT >= torsos.size()) {
                    currentIndT = 0;
                }
                currentTorso = torsos.get(currentIndT);
            }
        }

        // Bottoms
        else if (location.equals("Legs") && outfits.bottoms != null) {
            if (outfits.bottoms.size() > 0) {
                if (currentIndB >= outfits.bottoms.size()) {
                    currentIndB = 0;
                }
                currentBottoms = outfits.bottoms.get(currentIndB);
            }
        }

        // Shoes
        else if (location.equals("Feet") && outfits.shoes != null) {
            if (outfits.shoes.size() > 0) {
                if (currentIndS >= outfits.shoes.size()) {
                    currentIndS = 0;
                }
                currentShoes = outfits.shoes.get(currentIndS);
            }
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        return outfit;
    }

    @Override
    public Outfit previous(String location) {
        indexCalculator(location, -1); // Decrement the index of a clothing item by 1

        //Torso
        if (location.equals("Torso") && torsos.size() > 0) {
            // Inner
            if (currentIndT < 0) { // If the index has been decremented below 0 (gone all the way to the start)
                currentIndT = torsos.size() - 1; // then set the index to the end of the list
            }
            currentTorso = torsos.get(currentIndT);
        }
        // Bottoms
        else if (location.equals("Legs") && outfits.bottoms.size() > 0) {
            if (currentIndB < 0) {
                currentIndB = outfits.bottoms.size() - 1;
            }
            currentBottoms = outfits.inner_torso.get(currentIndB);
        }
        // Shoes
        else if (location.equals("Feet") && outfits.shoes.size() > 0) {
            if (currentIndS < 0) {
                currentIndS = outfits.shoes.size() - 1;
            }
            currentShoes = outfits.shoes.get(currentIndS);
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
        return outfit;
    }

    /**
     * Refresh button function (connected to button in the homescreen view model
     */
    @Override
    public Outfit refresh() {
        /* Increment each of the clothing items by 1*/
        indexCalculator("Torso", 1);
        indexCalculator("Legs", 1);
        indexCalculator("Feet", 1);

        if (torsos != null) {
            if (torsos.size() > 0) {
                // Inner Torso
                if (currentIndT >= torsos.size()) {
                    currentIndT = 0;
                }
                currentTorso = torsos.get(currentIndT);
            }
        }

        // Bottoms
        if (outfits.bottoms != null) {
            if (outfits.bottoms.size() > 0) {
                if (currentIndB >= outfits.bottoms.size()) {
                    currentIndB = 0;
                }
                currentBottoms = outfits.bottoms.get(currentIndB);
            }
        }

        // Shoes
        if (outfits.shoes != null) {
            if (outfits.shoes.size() > 0) {
                if (currentIndS >= outfits.shoes.size()) {
                    currentIndS = 0;
                }
                currentShoes = outfits.shoes.get(currentIndS);
            }
        }

        outfit = new Outfit(currentTorso, currentBottoms, currentShoes);
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

    private class AgentAsyncTask extends AsyncTask<Void, Void, Void> {
        final List<Clothing> clothingList;
        private final String location;
        private final ClothingCriteria criteria;
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
            if (clothingList.size() > 5 || criteria.preference.first < 0) {
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
                //return null;
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
            // Upper bound preference does not have to increase, since it is always max.
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
