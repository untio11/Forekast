package com.example.forekast.homescreen;


import android.location.Location;

import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Suggestion.SuggestionModule;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public abstract class HomeScreenViewModelInterface extends ViewModel {
    // The outfit that is currently shown on screen
    MutableLiveData<Outfit> currentOutfit = new MutableLiveData<>();
    // The weather that is currently shown on screen
    MutableLiveData<Weather> currentWeather = new MutableLiveData<>();
    // The criteria that are set with the sliders on the home screen
    ClothingCriteria clothingCriteria = new ClothingCriteria();
    // Local copy of the suggestion module to communicate with. Might be able to make it static
    protected SuggestionModule sugg = new SuggestionModule();

    /**
     * Used for linking the outfit livedata here with the observer in the homescreen activity.
     * @return A reference to the livedata containing the current outfit
     */
    abstract LiveData<Outfit> getLiveOutfit();

    /**
     * Used for linking the weather livedata here with the observer in the homescreen activity.
     * @return A reference to the livedata containing the current weather
     */
    abstract LiveData<Weather> getLiveWeather();

    /**
     * Used to get the current clothing criteria when the activity is reset
     * @return The currently set clothing criteria
     */
    abstract ClothingCriteria getClothingCriteria();

    /**
     * Set the upper bound of the warmth attribute of the current clothing criteria
     * @param new_warmth The new value for the upper bound of warmth
     */
    abstract void setWarmth(int new_warmth);

    /**
     * Return the current value for the upper bound of the warmth criteria
     * @return current upper bound value for warmth
     */
    abstract int getWarmth();

    abstract void setComfort(int new_comfort);
    abstract int getComfort();

    abstract void setFormality(int new_formality);
    abstract int getFormality();

    abstract public void setOwner(String new_owner);

    /**
     * Used to get the next element in the outfit powerset of the parsed location of clothing
     * @param clothing_location Location of the piece of clothing that needs to be switched: "Torso", "Legs", "Feet"
     */
    abstract void nextClothing(String clothing_location);

    /**
     * Used to get the previous element in the outfit powerset of the parsed location of clothing
     * @param clothing_location Location of the piece of clothing that needs to be switched: "Torso", "Legs", "Feet"
     */
    abstract void previousClothing(String clothing_location);

    abstract void refreshClothing();

    /**
     * Send a request for the weather to be fetched. Will update the livedata weather object, so nothing
     * else should have to be done.
     *
     * Also refreshes the accessory suggestions based on potential changes in weather.
     * @param name The name of the city we want the weather from
     */
    abstract public void updateWeather(String name);

    /**
     * Also sends a request for the weather to be fetched. It will also update the weather object,
     * but this one takes a location instead of just the name of the city.
     * @param location The location object as returned by the location library.
     */
    abstract public void updateWeather(Location location);
    /**
     * Refresh the entire outfit powerset in the suggestion module.
     */
    abstract void newOutfit();

    abstract boolean[] getAccessories();
}
