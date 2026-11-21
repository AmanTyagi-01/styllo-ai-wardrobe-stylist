package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey
    val userId: String = "default_user",
    val displayName: String = "Aman",
    val email: String = "aman@example.com",
    val vipTier: String = "VIP Studio",
    val isPro: Boolean = true,
    val streetwearScore: Int = 90,
    val casualScore: Int = 84,
    val minimalScore: Int = 72,
    val formalScore: Int = 38,
    val favoriteColors: String = "Onyx Black, Ivory White, Sand Beige, Slate Grey",
    val preferredFit: String = "Relaxed, Oversized",
    val locationWeather: String = "22°C • Partly Sunny • Mild Breeze",
    val totalPieces: Int = 42,
    val wardrobeUtilization: Int = 78
)
