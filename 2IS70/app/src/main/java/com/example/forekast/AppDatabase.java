package com.example.forekast;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Clothing.class}, version =  1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClothingDao clothingDao();
}
