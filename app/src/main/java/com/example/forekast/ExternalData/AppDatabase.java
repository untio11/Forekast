package com.example.forekast.ExternalData;

import com.example.forekast.Clothing.Clothing;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Clothing.class}, version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClothingDao clothingDao();
}
