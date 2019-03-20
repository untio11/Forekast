package com.example.forekast;

import com.example.forekast.clothing.Clothing;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ClothingDao {
    @Insert
    void insertAll(Clothing ... pieces);

    @Delete
    void  delete(Clothing piece);

    @Query("Select * From clothing")
    List<Clothing> getAll();
}
