package com.example.forekast.ExternalData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.forekast.Clothing.Clothing;

import java.util.List;

/**
 * This class defines the interface with the database.
 */
@Dao
public interface ClothingDao {

    /**
     * Insert a single piece of clothing and return the ID
     *
     * @param piece The piece of clothing to be added
     * @return The ID of the added piece
     */
    @Insert
    long insert(Clothing piece);

    /**
     * Update the given piece. Useful for the edit screen when editing existing clothing.
     * @param pieces The pieces that should be updated in the database.
     */
    @Update
    void updateAll(Clothing... pieces);

    /**
     * Delete a single piece of clothing from the database.
     * @param piece The piece to be deleted.
     */
    @Delete
    void delete(Clothing piece);

    /**
     * Get all the clothing. Mostly for testing purposes.
     * @return The entire database.
     */
    @Query("Select * From clothing")
    List<Clothing> getAll();

    /**
     * Get a piece of clothing by it's ID. Also mostly for testing.
     * @param ID The ID of the piece of clothing.
     * @return The piece of clothing with that ID.
     */
    @Query("Select * From clothing Where ID = :ID Limit 1")
    Clothing getByID(long ID);

    /**
     * Most used. Get the list of clothing by the location and the owner.
     * @param owner The owner of the pieces of clothing.
     * @param location The location "Torso" "Legs" or "Feet"
     * @return List of clothing such that they all have the requested owner and location.
     */
    @Query("Select * From clothing Where location = :location and owner = :owner")
    List<Clothing> getByLocation(String owner, String location);
}
