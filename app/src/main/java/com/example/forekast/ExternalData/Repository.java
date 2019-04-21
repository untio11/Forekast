package com.example.forekast.ExternalData;

import android.content.Context;
import android.preference.PreferenceManager;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.Forekast;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

/**
 * A static wrapper for all external data access.
 */
public class Repository {
    private static AppDatabase db;
    private static Context context;

    /**
     * Set the database instance for the repository. Needs to be done externally because of lifecycle awareness
     *
     * @param appcontext The context of the application where the database will be used
     */
    public static void initDB(final Context appcontext) throws IllegalArgumentException {
        if (appcontext == null) {
            throw new IllegalArgumentException("The given appcontext should not be null");
        }
        context = appcontext;
        if (db == null) {
            db = Room.databaseBuilder(
                    appcontext,
                    AppDatabase.class,
                    "clothing").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
    }

    /**
     * Request the weather at the city parsed and update the parsed livedata object
     *
     * @param weather A mutable livedata object that will be updated with the most recent weather
     * @param city    The name of the city to be looked up
     */
    public static void getWeather(String city, MutableLiveData<Weather> weather) {
        new WeatherAPI(city).execute(weather);
    }

    /**
     * Request the weather at the lat, lon parsed and update the parsed livedata object
     *
     * @param weather A mutable livedata object that will be updated with the most recent weather
     * @param lat     The latitude of the location to be looked up
     * @param lon     The longitude of the location to be looked up
     */
    public static void getWeather(String lat, String lon, MutableLiveData<Weather> weather) {
        new WeatherAPI(lat, lon).execute(weather);
    }

    /**
     * Get a list of clothing for a specific body location according to the clothingcriteria
     *
     * @param location Torso, Legs or Feet: the location of the requested clothing
     * @param criteria Upper and lower bounds for the different attributes of the clothing
     * @return A filtered list such that all the pieces of clothing in that list are within the bounds imposed by the criteria
     * @throws NullPointerException     if the database has not been instantiated beforehand
     * @throws IllegalArgumentException if the clothingcriteria object is null
     */
    public static List<Clothing> getClothing(final String location, final ClothingCriteria criteria) throws NullPointerException, IllegalArgumentException {
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
     *
     * @param clothing The list containing the clothing.
     * @param criteria The criteria to filter the clothing on
     * @return A list of clothing containing just the clothing that conforms to the criteria: criteria.attribute.first < clothing.attribute < criteria.attribute.second. Possibly empty.
     */
    private static List<Clothing> filter(List<Clothing> clothing, ClothingCriteria criteria) {
        Stream<Clothing> clothing_stream = clothing.stream();

        // Filter the retrieved clothing based on the criteria
        clothing_stream = clothing_stream.filter(piece -> (
                piece.warmth >= criteria.warmth.first && piece.warmth <= criteria.warmth.second
        ));
        clothing_stream = clothing_stream.filter(piece -> (
                piece.formality >= criteria.formality.first && piece.formality <= criteria.formality.second
        ));
        clothing_stream = clothing_stream.filter(piece -> (
                piece.comfort >= criteria.comfort.first && piece.comfort <= criteria.comfort.second
        ));

        // Either all clothing or only everything that's not in the washing machine
        clothing_stream = clothing_stream.filter(piece -> (
                criteria.washingMachine || !piece.washing_machine || !PreferenceManager.getDefaultSharedPreferences(context).getBoolean("availability_system", true)
        ));

        return clothing_stream.collect(Collectors.toList());
    }

    /**
     * Add the pieces of clothing to the database and set their ID's accordingly
     *
     * @param clothing Pieces of clothing to be added to the database
     * @throws NullPointerException if the database has not been instantiated yet
     */
    public static void addClothing(Clothing... clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        for (Clothing piece : clothing) {
            piece.ID = db.clothingDao().insert(piece);
        }
    }

    /**
     * Update the values of a piece of clothing in the database. Used for editing pieces after first
     * insertion.
     *
     * @param clothing The piece of clothing to be edited.
     * @throws NullPointerException When the database is not instantiated yet.
     */
    public static void updateClothing(Clothing clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        db.clothingDao().updateAll(clothing);
    }

    /**
     * Remove the parsed pieces of clothing from the database
     *
     * @param clothing Pieces of clothing to be removed
     * @throws NullPointerException if the database has not been instantiated yet
     */
    public static void removeClothing(Clothing... clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        for (Clothing piece : clothing) {
            db.clothingDao().delete(piece);
        }
    }
}
