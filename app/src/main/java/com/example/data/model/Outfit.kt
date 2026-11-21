package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "outfits")
data class Outfit(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "default_user",
    val title: String,
    val occasion: String, // Casual, Wedding, Campus, Office, Night Out, Date Night, Travel, Activewear, Festival, Custom
    val style: String, // Streetwear, Clean Minimal, Smart Casual, Luxury Classic, Vintage 90s, Relaxed Cozy
    val harmonyScore: Int = 94, // 0-100%
    val stylistRationale: String = "",
    val climateNote: String = "",
    val heroImageUrl: String = "",
    val upperwearId: String = "",
    val upperwearName: String = "",
    val bottomwearId: String = "",
    val bottomwearName: String = "",
    val footwearId: String = "",
    val footwearName: String = "",
    val accessoryId: String = "",
    val accessoryName: String = "",
    val isLiked: Boolean = false,
    val isDisliked: Boolean = false,
    val isSaved: Boolean = false,
    val isWorn: Boolean = false,
    val wornDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
