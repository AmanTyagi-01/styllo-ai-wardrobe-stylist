package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "wardrobe_items")
data class WardrobeItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "default_user",
    val name: String,
    val category: String, // Upperwear, Bottomwear, Outerwear, Footwear, Accessories, Custom
    val subcategory: String = "",
    val imageUrl: String = "",
    val thumbnailUrl: String = "",
    val primaryColour: String = "Onyx",
    val secondaryColours: String = "",
    val pattern: String = "Solid",
    val fit: String = "Relaxed", // Oversized, Tailored, Relaxed, Slim
    val styleTags: String = "Casual", // Streetwear, Minimalist, Smart Casual, etc.
    val season: String = "All Season",
    val formality: String = "Casual", // Casual, Smart Casual, Formal
    val material: String = "Cotton",
    val wearCount: Int = 0,
    val favourite: Boolean = false,
    val archived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
