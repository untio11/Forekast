package com.example.forekast;

import android.content.Context;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.Torso;
import com.example.forekast.ExternalData.AppDatabase;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DBTest {
    private static AppDatabase db;

    @BeforeClass
    public static void init() {
        // Context of the app under test.
        Context appcontext = InstrumentationRegistry.getTargetContext();
        db = Room.databaseBuilder(appcontext, AppDatabase.class, "clothing").fallbackToDestructiveMigration().build();
    }

    @Test
    public void testInsertandDel() {
        Clothing tshirt = new Torso();
        tshirt.type = "T-Shirt";
        tshirt.comfort = 10;
        tshirt.formality = 2;
        tshirt.warmth = 69;
        tshirt.owner = "Hans";

        // This is not needed when adding clothing through the repository
        tshirt.ID = db.clothingDao().insert(tshirt);

        Clothing result = db.clothingDao().getByID(tshirt.ID);

        assertEquals(tshirt.toString(), result.toString());

        db.clothingDao().delete(result);
    }

    @Test
    public void testWearableProperties() {
        Clothing tshirt = new Torso();
        tshirt.type = "T-Shirt";
        tshirt.preSet();
        tshirt.ID = db.clothingDao().insert(tshirt);
        assertTrue(tshirt.underwearable);
        assertFalse(tshirt.overwearable);

        Clothing sweater = new Torso();
        sweater.type = "Sweater";
        sweater.preSet();
        sweater.ID = db.clothingDao().insert(sweater);
        assertFalse(sweater.underwearable);
        assertTrue(sweater.overwearable);

        Clothing shirt = new Torso();
        shirt.type = "Shirt";
        shirt.preSet();
        shirt.ID = db.clothingDao().insert(shirt);
        assertTrue(shirt.underwearable);
        assertTrue(shirt.overwearable);

        assertTrue((db.clothingDao().getByID(tshirt.ID)).underwearable);
        assertFalse((db.clothingDao().getByID(tshirt.ID)).overwearable);

        assertFalse((db.clothingDao().getByID(sweater.ID)).underwearable);
        assertTrue((db.clothingDao().getByID(sweater.ID)).overwearable);

        assertTrue((db.clothingDao().getByID(shirt.ID)).underwearable);
        assertTrue((db.clothingDao().getByID(shirt.ID)).overwearable);
    }

    private void clearDB() {
        List<Clothing> all = db.clothingDao().getAll();
        for (Clothing piece : all) {
            db.clothingDao().delete(piece);
        }
    }
}
