package com.example.forekast.external_data;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
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

    public static <T extends Clothing> List<T> getClothing(String type, ClothingCriteria criteria) throws NullPointerException {
        if (db == null) {
            throw new NullPointerException("The database has not been instantiated yet");
        }

        return null;
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
