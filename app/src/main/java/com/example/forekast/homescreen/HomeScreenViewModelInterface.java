package com.example.forekast.homescreen;

import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Weather;
import com.example.forekast.outfits.Outfit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


abstract class HomeScreenViewModelInterface extends ViewModel {
    // The outfit that is currently shown on screen
    MutableLiveData<Outfit> currentOutfit = new MutableLiveData<>();
    // The weather that is currently shown on screen
    MutableLiveData<Weather> currentWeather = new MutableLiveData<>();
    // The criteria that are set with the sliders on the home screen
    ClothingCriteria clothingCriteria;
    // Local copy of the suggestion module to communicate with. Might be able to make it static
    protected SuggestionModuleInterface sugg = new SuggestionModule();

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
     * Used to update the viewmodel based on the state of the sliders on the home screen.
     * Might change the method signature to take some integers, so the home screen activity doesn't
     * have a dependency on ClothingCriteria
     * @param criteria The values that are set with the sliders on the homescreen
     */
    abstract void setClothingCriteria(ClothingCriteria criteria);

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

    /**
     * Send a request for the weather to be fetched. Will update the livedata weather object, so nothing
     * else should have to be done.
     */
    abstract void updateWeather();

    /**
     * Refresh the entire outfit powerset in the suggestion module.
     */
    abstract void newOutfit();
}
