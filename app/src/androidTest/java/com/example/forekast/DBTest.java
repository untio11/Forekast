package com.example.forekast;

import android.content.Context;
import android.util.Log;

import com.example.forekast.clothing.Boots;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.Torso;
import com.example.forekast.clothing.Tshirt;
import com.example.forekast.external_data.AppDatabase;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DBTest {
    Context appcontext;
    AppDatabase db;

    @Before
    public void init() {
        // Context of the app under test.
        appcontext = InstrumentationRegistry.getTargetContext();
        db = Room.databaseBuilder(appcontext, AppDatabase.class, "clothing").fallbackToDestructiveMigration().build();
    }

    @Test
    public void testInsertandDel() {
        Clothing tshirt = new Tshirt();
        tshirt.comfort = 10;
        tshirt.formality = 2;
        tshirt.warmth = 69;
        tshirt.owner = "Hans";

        tshirt.ID = db.clothingDao().insert(tshirt);
        Clothing result = db.clothingDao().getByID(tshirt.ID);

        assertEquals(tshirt.toString(), result.toString());

        db.clothingDao().delete(result);
    }

    private void clearDB() {
        List<Clothing> all = db.clothingDao().getAll();
        for (Clothing piece : all) {
            db.clothingDao().delete(piece);
        }
    }
}
