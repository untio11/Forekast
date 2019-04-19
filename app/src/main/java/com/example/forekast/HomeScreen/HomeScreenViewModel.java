package com.example.forekast.HomeScreen;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.ExternalData.Repository;
import com.example.forekast.ExternalData.Weather;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Suggestion.SuggestionModule;

/**
 * This view model keeps track of the current state of the home screen, which includes the weather,
 * the outfits, the current user and the position of the sliders for the criteria.
 */
public class HomeScreenViewModel extends ViewModel {
    // The outfit that is currently shown on screen
    final MutableLiveData<Outfit> currentOutfit = new MutableLiveData<>();
    // The weather that is currently shown on screen
    private final MutableLiveData<Weather> currentWeather = new MutableLiveData<>();
    // Local copy of the suggestion module to communicate with. Might be able to make it static
    final SuggestionModule sugg = new SuggestionModule();
    // The criteria that are set with the sliders on the home screen
    ClothingCriteria clothingCriteria = new ClothingCriteria();

    /**
     * Used for linking the outfit livedata here with the observer in the homescreen activity.
     *
     * @return A reference to the livedata containing the current outfit
     */
    LiveData<Outfit> getLiveOutfit() {
        return currentOutfit;
    }

    /**
     * Used for linking the weather livedata here with the observer in the homescreen activity.
     *
     * @return A reference to the livedata containing the current weather
     */
    public LiveData<Weather> getLiveWeather() {
        return currentWeather;
    }

    /**
     * Used to get the current clothing criteria when the activity is reset
     *
     * @return The currently set clothing criteria
     */
    ClothingCriteria getClothingCriteria() {
        return clothingCriteria;
    }

    /**
     * Return the current value for the upper bound of the warmth criteria
     *
     * @return current upper bound value for warmth
     */
    int getWarmth() {
        return clothingCriteria.warmth.second;
    }

    /**
     * Set the upper bound of the warmth attribute of the current clothing criteria
     *
     * @param new_warmth The new value for the upper bound of warmth
     */
    void setWarmth(int new_warmth) {
        clothingCriteria.warmth.second = new_warmth;
    }

    int getComfort() {
        return clothingCriteria.comfort.second;
    }

    void setComfort(int new_comfort) {
        clothingCriteria.comfort.second = new_comfort;
    }

    int getFormality() {
        return clothingCriteria.formality.second;
    }

    void setFormality(int new_formality) {
        clothingCriteria.formality.second = new_formality;
    }

    public void setOwner(String new_owner) {
        clothingCriteria.owner = new_owner;
    }

    /**
     * Used to get the next element in the outfit powerset of the parsed location of clothing
     *
     * @param clothing_location Location of the piece of clothing that needs to be switched: "Torso", "Legs", "Feet"
     */
    void nextClothing(String clothing_location) {
        currentOutfit.postValue(sugg.next(clothing_location));
    }

    /**
     * Used to get the previous element in the outfit powerset of the parsed location of clothing
     *
     * @param clothing_location Location of the piece of clothing that needs to be switched: "Torso", "Legs", "Feet"
     */
    void previousClothing(String clothing_location) {
        currentOutfit.postValue(sugg.previous(clothing_location));
    }

    /**
     * Request a full refresh of the outfit that is shown.
     */
    void refreshClothing() {
        currentOutfit.postValue(sugg.refresh());
    }

    /**
     * Send a request for the weather to be fetched. Will update the livedata weather object, so nothing
     * else should have to be done.
     *
     * @param name The name of the city we want the weather from
     */
    public void updateWeather(String name) {
        Repository.getWeather(name, currentWeather);
    }

    /**
     * Also sends a request for the weather to be fetched. It will also update the weather object,
     * but this one takes a location instead of just the name of the city.
     *
     * @param location The location object as returned by the location library.
     */
    public void updateWeather(Location location) {
        Repository.getWeather(
                Double.toString(location.getLatitude()),
                Double.toString(location.getLongitude()),
                currentWeather
        );
    }

    /**
     * Refresh the entire outfit powerset in the suggestion module.
     */
    void newOutfit() {
        currentOutfit.postValue(sugg.setOutfit());
    }

    /**
     * Get the state of the accessories in the form of a boolean array.
     * @return The current state of the accessories based on the weather.
     */
    boolean[] getAccessories() {
        return sugg.getAccessories();
    }

    /**
     * Return the current value of the weather.
     * @return The current weather.
     */
    Weather getWeather() {
        return currentWeather.getValue();
    }
}
