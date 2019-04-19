package com.example.forekast.ExternalData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forekast.Clothing.Clothing;

import java.util.List;

@Dao
public interface ClothingDao {

    /**
     * Insert a single piece of clothing and return the ID
     *
     * @param piece The piece of clothing to be added
     * @return The ID of the added piece
     */
    @Insert
    long insert(Clothing piece); // Insert just one piece and return the ID

    @Update
    void updateAll(Clothing... pieces);

    @Delete
    void delete(Clothing piece);

    @Query("Select * From clothing")
    List<Clothing> getAll();

    @Query("Select * From clothing Where ID = :ID Limit 1")
    Clothing getByID(long ID);

    @Query("Select * From clothing Where location = :location and owner = :owner and washing_machine = 0")
    List<Clothing> getByLocation(String owner, String location);

    @Query("Select * From clothing Where location = :location and owner = :owner")
    List<Clothing> getByLocationWashing(String owner, String location);

}
