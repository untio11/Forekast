package com.example.forekast;

import com.example.forekast.clothing.Clothing;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Clothing.class}, version =  1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClothingDao clothingDao();
}
