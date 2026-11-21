package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Outfit
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {
    @Query("SELECT * FROM outfits ORDER BY createdAt DESC")
    fun getAllOutfits(): Flow<List<Outfit>>

    @Query("SELECT * FROM outfits WHERE isSaved = 1 ORDER BY createdAt DESC")
    fun getSavedOutfits(): Flow<List<Outfit>>

    @Query("SELECT * FROM outfits WHERE isWorn = 1 ORDER BY wornDate DESC")
    fun getWornHistory(): Flow<List<Outfit>>

    @Query("SELECT * FROM outfits WHERE id = :id LIMIT 1")
    suspend fun getOutfitById(id: String): Outfit?

    @Query("SELECT * FROM outfits ORDER BY createdAt DESC")
    suspend fun getAllOutfitsSync(): List<Outfit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOutfit(outfit: Outfit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(outfits: List<Outfit>)

    @Update
    suspend fun updateOutfit(outfit: Outfit)

    @Delete
    suspend fun deleteOutfit(outfit: Outfit)

    @Query("UPDATE outfits SET isSaved = :isSaved WHERE id = :id")
    suspend fun updateSaved(id: String, isSaved: Boolean)

    @Query("UPDATE outfits SET isLiked = :isLiked, isDisliked = 0 WHERE id = :id")
    suspend fun recordLike(id: String, isLiked: Boolean = true)

    @Query("UPDATE outfits SET isDisliked = :isDisliked, isLiked = 0 WHERE id = :id")
    suspend fun recordDislike(id: String, isDisliked: Boolean = true)

    @Query("UPDATE outfits SET isWorn = 1, wornDate = :timestamp WHERE id = :id")
    suspend fun markAsWorn(id: String, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM outfits WHERE isSaved = 0 AND isWorn = 0")
    suspend fun clearUnsavedOutfits()
}
