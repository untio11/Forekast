package com.example.forekast.ExternalData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.forekast.Clothing.Clothing;

@Database(entities = {Clothing.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClothingDao clothingDao();
}
