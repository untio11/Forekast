package com.example.forekast;

import android.content.Context;

import com.example.forekast.clothing.*;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

import androidx.lifecycle.MutableLiveData;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class RepoTest {

    @BeforeClass
    public static void init() {
        Context appcontext = InstrumentationRegistry.getTargetContext();
        Repository.initDB(appcontext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        Repository.initDB(null);
    }

    @Test
    public void testAddClothing() {
        Clothing tshirt = new Torso();
        tshirt.type = "T-Shirt";
        tshirt.comfort = 12;
        tshirt.warmth = 4;
        tshirt.formality = 88;
        tshirt.owner = "hans";
        tshirt.overwearable = false;
        tshirt.underwearable = true;

        Clothing pant = new Legs();
        pant.type = "Jeans";
        pant.comfort = 5;
        pant.warmth = 76;
        pant.formality = 1;
        pant.owner = "hans";

        Clothing footglove = new Feet();
        footglove.type = "Boots";
        footglove.comfort = 69;
        footglove.warmth = 76;
        footglove.formality = 1;
        footglove.owner = "hans";

        // On init, clothing should have ID of 0, as it has not been added to the database yet
        assertEquals(0, tshirt.ID);
        assertEquals(0, pant.ID);
        assertEquals(0, footglove.ID);

        Repository.addClothing(tshirt, pant, footglove);

        // After adding, the ID should have been set to some other value
        assertNotEquals(0, tshirt.ID);
        assertNotEquals(0, pant.ID);
        assertNotEquals(0, footglove.ID);
    }

    @Test
    public void testGetClothing() {
        Clothing tshirt = new Torso();
        tshirt.type = "Torso";
        tshirt.comfort = 1;
        tshirt.warmth = 55;
        tshirt.formality = 23;
        tshirt.owner = "hans";
        tshirt.overwearable = false;
        tshirt.underwearable = true;

        // Dummy criteria: should accept all clothing of the appropriate type, owned by hans
        ClothingCriteriaInterface criteria = new ClothingCriteria();
        criteria.owner = "hans";

        // To ensure the result will never be empty
        Repository.addClothing(tshirt);

        List<Clothing> result = Repository.getClothing("Torso", criteria);

        // The t-shirt should at least be found
        assertTrue(result.size() > 0);

        // And all the results should only be torso pieces
        for (Clothing piece : result) {
            assertEquals("Torso", piece.location);
            assertEquals("hans", piece.owner);
        }
    }

    @Test
    public void testGetClothingWithCriteria() {
        Clothing tshirt = new Torso();
        tshirt.type = "T-Shirt";
        tshirt.comfort = 1;
        tshirt.warmth = 5;
        tshirt.formality = 23;
        tshirt.owner = "hans";
        tshirt.overwearable = false;
        tshirt.underwearable = true;

        Clothing tshirt2 = new Torso();
        tshirt2.type = "T-Shirt";
        tshirt2.comfort = 1;
        tshirt2.warmth = 16;
        tshirt2.formality = 23;
        tshirt2.owner = "hans";
        tshirt2.overwearable = false;
        tshirt2.underwearable = true;

        // Dummy criteria: should accept clothing of the appropriate type between warmth bounds, owned by hans
        ClothingCriteriaInterface criteria = new ClothingCriteria();
        criteria.owner = "hans";
        criteria.warmth.second = 10;
        criteria.warmth.first = 0;

        // To ensure the result will never be empty
        Repository.addClothing(tshirt, tshirt2);

        List<Clothing> result = Repository.getClothing("Torso", criteria);

        // The t-shirt should at least be found
        assertTrue(result.size() > 0);

        // And all the results should only be torso pieces
        for (Clothing piece : result) {
            assertEquals("Torso", piece.location);
            assertEquals("hans", piece.owner);
            assertTrue(piece.warmth < 10 && piece.warmth > 0);
        }
    }

    @Test
    public void testWeatherAPICityNameSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("Eindhoven", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertEquals("Eindhoven", weather.getValue().getCity());
        assertNotEquals(0.0, weather.getValue().getTemp());
    }

    @Test
    public void testWeatherAPICityTempSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("Eindhoven", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getTemp());
    }

    @Test
    public void testWeatherAPICityFeelsLikeSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("Eindhoven", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getFeels_like());
    }

    @Test
    public void testWeatherAPICityPrecipSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("Eindhoven", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getPrecipitation());
    }

    @Test
    public void testWeatherAPICityUVSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("Eindhoven", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getUv_index());
    }


    @Test
    public void testWeatherAPICityWindSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("Eindhoven", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getWind());
    }

    @Test
    public void testWeatherAPILatLonNameSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("51.4393", "5.4786", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertEquals("Eindhoven", weather.getValue().getCity());
        assertNotEquals(0.0, weather.getValue().getTemp());
    }

    @Test
    public void testWeatherAPILatLonTempSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("51.4393", "5.4786", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getTemp());
    }

    @Test
    public void testWeatherAPILatLonFeelsLikeSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("51.4393", "5.4786", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getFeels_like());
    }

    @Test
    public void testWeatherAPILatLonPrecipSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("51.4393", "5.4786", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getPrecipitation());
    }

    @Test
    public void testWeatherAPILatLonUVSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("51.4393", "5.4786", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getUv_index());
    }


    @Test
    public void testWeatherAPILatLonWindSet() throws InterruptedException {
        MutableLiveData<Weather> weather = new MutableLiveData<>();
        Repository.getWeather("51.4393", "5.4786", weather);

        synchronized (this) {
            this.wait(2000);
        }

        assertNotNull(weather.getValue());
        assertNotEquals(0.0, weather.getValue().getWind());
    }
}
