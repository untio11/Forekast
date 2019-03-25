package com.example.forekast.external_data;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteriaInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.lifecycle.MutableLiveData;

public class Repository {
    private static AppDatabase db;

    /**
     * Set the database instance for the repository. Needs to be done externally because of lifecycle awareness
     * @param dbinstance The appdatabase instance to be used
     */
    public static void setDB(final AppDatabase dbinstance) {
        db = dbinstance;
    }

    /**
     * Request the weather and update the parsed livedata object
     * @param weather A mutable livedata object that will be updated with the most recent weather
     */
    public static void getWeather(MutableLiveData<Weather> weather) {
        new WeatherAPI().execute(weather);
    }

    /**
     * Get a list of clothing for a specific body location according to the clothingcriteria
     * @param location Torso, Legs or Feet: the location of the requested clothing
     * @param criteria Upper and lower bounds for the different attributes of the clothing
     * @return A filtered list such that all the pieces of clothing in that list are within the bounds imposed by the criteria
     * @throws NullPointerException if the database has not been instantiated beforehand
     * @throws IllegalArgumentException if the clothingcriteria object is null
     */
    public static List<Clothing> getClothing(final String location, final ClothingCriteriaInterface criteria) throws NullPointerException, IllegalArgumentException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        } else if (criteria == null) {
            throw new IllegalArgumentException("Criteria were not set!");
        }

        List<Clothing> result = db.clothingDao().getByLocation(criteria.owner, location);

        return filter(result, criteria);
    }

    /**
     * Filter the given clothing list based on the parsed criteria
     * @param clothing The list containing the clothing.
     * @param criteria The criteria to filter the clothing on
     * @return A list of clothing containing just the clothing that conforms to the criteria. Possibly empty
     */
    private static List<Clothing> filter(List<Clothing> clothing, ClothingCriteriaInterface criteria) {
        Stream<Clothing> clothing_stream = clothing.stream();

        // Filter the retrieved clothing based on the criteria
        clothing_stream = clothing_stream.filter(piece -> (piece.warmth > criteria.warmth.first && piece.warmth < criteria.warmth.second));
        clothing_stream = clothing_stream.filter(piece -> (piece.formality > criteria.formality.first && piece.formality < criteria.formality.second));
        clothing_stream = clothing_stream.filter(piece -> (piece.comfort > criteria.comfort.first && piece.comfort < criteria.comfort.second));

        return clothing_stream.collect(Collectors.toList());
    }

    /**
     * Add the pieces of clothing to the database and set their ID's accordingly
     * @param clothing Pieces of clothing to be added to the database
     * @throws NullPointerException if the database has not been instantiated yet
     */
    public static void addClothing(Clothing ... clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        for (Clothing piece : clothing) {
            piece.ID = db.clothingDao().insert(piece);
        }
    }

    /**
     * Remove the parsed pieces of clothing from the database
     * @param clothing Pieces of clothing to be removed
     * @throws NullPointerException if the database has not been instantiated yet
     */
    public static void removeClothing(Clothing ... clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }
        for (Clothing piece : clothing) {
            db.clothingDao().delete(piece);
        }
    }
}
