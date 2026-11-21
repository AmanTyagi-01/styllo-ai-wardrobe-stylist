package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.WardrobeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WardrobeDao {
    @Query("SELECT * FROM wardrobe_items WHERE archived = 0 ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<WardrobeItem>>

    @Query("SELECT * FROM wardrobe_items WHERE archived = 0 AND category = :category ORDER BY createdAt DESC")
    fun getItemsByCategory(category: String): Flow<List<WardrobeItem>>

    @Query("SELECT * FROM wardrobe_items WHERE id = :id LIMIT 1")
    suspend fun getItemById(id: String): WardrobeItem?

    @Query("SELECT * FROM wardrobe_items WHERE favourite = 1 AND archived = 0")
    fun getFavoriteItems(): Flow<List<WardrobeItem>>

    @Query("SELECT COUNT(*) FROM wardrobe_items WHERE archived = 0")
    fun getItemsCount(): Flow<Int>

    @Query("SELECT * FROM wardrobe_items WHERE archived = 0")
    suspend fun getAllItemsSync(): List<WardrobeItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WardrobeItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<WardrobeItem>)

    @Update
    suspend fun updateItem(item: WardrobeItem)

    @Delete
    suspend fun deleteItem(item: WardrobeItem)

    @Query("UPDATE wardrobe_items SET favourite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE wardrobe_items SET wearCount = wearCount + 1, updatedAt = :timestamp WHERE id = :id")
    suspend fun incrementWearCount(id: String, timestamp: Long = System.currentTimeMillis())
}
