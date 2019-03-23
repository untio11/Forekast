package com.example.forekast.external_data;

import com.example.forekast.clothing.Clothing;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Clothing.class}, version =  2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClothingDao clothingDao();
}
