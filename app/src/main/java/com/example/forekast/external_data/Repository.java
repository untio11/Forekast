package com.example.forekast.external_data;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteriaInterface;

import java.util.List;
import androidx.lifecycle.MutableLiveData;

public class Repository {
    static AppDatabase db;

    public static void setDB(final AppDatabase dbinstance) {
        db = dbinstance;
    }

    public static void getWeather(MutableLiveData<Weather> weather) {
        new WeatherAPI().execute(weather);
    }

    public static List<Clothing> getClothing(ClothingCriteriaInterface criteria) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        } else if (criteria == null) {
            throw new IllegalArgumentException("Criteria were not set!");
        }

        List<Clothing> result = db.clothingDao().getByLocation(criteria.owner, criteria.location);


        return result;
    }

    public static void addClothing(Clothing ... clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        db.clothingDao().insertAll(clothing);
    }

    public static void removeClothing(Clothing clothing) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        db.clothingDao().delete(clothing);
    }
}
