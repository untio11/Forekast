package com.example.forekast.external_data;

import com.example.forekast.clothing.Clothing;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClothingDao {
    /**
     * Not to be used, as it does not properly set the ID's of the inserted items.
     * @param pieces The pieces of clothing to be added
     */
    @Insert
    void insertAll(Clothing ... pieces);

    /**
     * Insert a single piece of clothing and return the ID
     * @param piece The piece of clothing to be added
     * @return The ID of the added piece
     */
    @Insert
    long insert(Clothing piece); // Insert just one piece and return the ID

    @Update
    void updateAll(Clothing ... pieces);

    @Delete
    void delete(Clothing piece);

    @Query("Select * From clothing")
    List<Clothing> getAll();

    @Query("Select * From clothing Where ID = :ID Limit 1")
    Clothing getByID(long ID);

    @Query("Select * From clothing Where location = :location and owner = :owner")
    List<Clothing> getByLocation(String owner, String location);

    @Query("Select * From clothing Where type = :type and owner = :owner")
    List<Clothing> getByType(String owner, String type);

    @Query("Select * From clothing Where owner = :owner")
    List<Clothing> getByOwner(String owner);
}
