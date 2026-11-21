package com.example.data.repository

import android.content.Context
import com.example.data.local.StylloDatabase
import com.example.data.model.Outfit
import com.example.data.model.UserProfile
import com.example.data.model.WardrobeItem
import com.example.engine.StylingRecommendationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class StylloRepository(context: Context) {
    private val db = StylloDatabase.getDatabase(context)
    private val wardrobeDao = db.wardrobeDao()
    private val outfitDao = db.outfitDao()
    private val userDao = db.userDao()

    val allWardrobeItems: Flow<List<WardrobeItem>> = wardrobeDao.getAllItems()
    val allOutfits: Flow<List<Outfit>> = outfitDao.getAllOutfits()
    val savedOutfits: Flow<List<Outfit>> = outfitDao.getSavedOutfits()
    val wornHistory: Flow<List<Outfit>> = outfitDao.getWornHistory()
    val userProfile: Flow<UserProfile?> = userDao.getProfile()
    val wardrobeCount: Flow<Int> = wardrobeDao.getItemsCount()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            seedInitialDataIfEmpty()
        }
    }

    private suspend fun seedInitialDataIfEmpty() {
        val count = wardrobeDao.getAllItemsSync().size
        if (count == 0) {
            val initialProfile = UserProfile(
                userId = "default_user",
                displayName = "Aman",
                vipTier = "VIP Studio",
                isPro = true,
                streetwearScore = 90,
                casualScore = 84,
                minimalScore = 72,
                formalScore = 38,
                favoriteColors = "Onyx Black, Ivory White, Sand Beige, Slate Grey",
                preferredFit = "Relaxed, Oversized",
                locationWeather = "22°C • Partly Sunny • Mild Breeze",
                totalPieces = 42,
                wardrobeUtilization = 78
            )
            userDao.insertProfile(initialProfile)

            val seedItems = createSeedWardrobeItems()
            wardrobeDao.insertAll(seedItems)

            // Seed initial 5 curated outfits
            val criteria = StylingRecommendationEngine.GenerationCriteria(
                occasion = "Casual",
                vibes = listOf("Clean Minimal", "Streetwear")
            )
            val initialOutfits = StylingRecommendationEngine.generateOutfits(seedItems, criteria)
            outfitDao.insertAll(initialOutfits)
        }
    }

    suspend fun addWardrobeItem(item: WardrobeItem) {
        wardrobeDao.insertItem(item)
    }

    suspend fun updateWardrobeItem(item: WardrobeItem) {
        wardrobeDao.updateItem(item)
    }

    suspend fun deleteWardrobeItem(item: WardrobeItem) {
        wardrobeDao.deleteItem(item)
    }

    suspend fun toggleFavoriteItem(id: String, currentVal: Boolean) {
        wardrobeDao.updateFavorite(id, !currentVal)
    }

    suspend fun toggleSaveOutfit(id: String, currentVal: Boolean) {
        outfitDao.updateSaved(id, !currentVal)
    }

    suspend fun recordOutfitFeedback(id: String, liked: Boolean) {
        if (liked) {
            outfitDao.recordLike(id)
        } else {
            outfitDao.recordDislike(id)
        }
    }

    suspend fun markOutfitWorn(outfit: Outfit) {
        outfitDao.markAsWorn(outfit.id)
        if (outfit.upperwearId.isNotEmpty()) wardrobeDao.incrementWearCount(outfit.upperwearId)
        if (outfit.bottomwearId.isNotEmpty()) wardrobeDao.incrementWearCount(outfit.bottomwearId)
        if (outfit.footwearId.isNotEmpty()) wardrobeDao.incrementWearCount(outfit.footwearId)
        if (outfit.accessoryId.isNotEmpty()) wardrobeDao.incrementWearCount(outfit.accessoryId)
    }

    suspend fun generateNewOutfits(criteria: StylingRecommendationEngine.GenerationCriteria): List<Outfit> {
        val wardrobe = wardrobeDao.getAllItemsSync()
        val generated = StylingRecommendationEngine.generateOutfits(wardrobe, criteria)
        outfitDao.clearUnsavedOutfits()
        outfitDao.insertAll(generated)
        return generated
    }

    suspend fun swapOutfitGarment(outfit: Outfit, category: String, newItem: WardrobeItem): Outfit {
        val (newScore, newRationale) = StylingRecommendationEngine.recalculateHarmonyScore(outfit, category, newItem)
        val updated = when (category.lowercase()) {
            "upperwear" -> outfit.copy(
                upperwearId = newItem.id,
                upperwearName = newItem.name,
                harmonyScore = newScore,
                stylistRationale = newRationale
            )
            "bottomwear" -> outfit.copy(
                bottomwearId = newItem.id,
                bottomwearName = newItem.name,
                harmonyScore = newScore,
                stylistRationale = newRationale
            )
            "footwear" -> outfit.copy(
                footwearId = newItem.id,
                footwearName = newItem.name,
                harmonyScore = newScore,
                stylistRationale = newRationale
            )
            else -> outfit.copy(
                accessoryId = newItem.id,
                accessoryName = newItem.name,
                harmonyScore = newScore,
                stylistRationale = newRationale
            )
        }
        outfitDao.updateOutfit(updated)
        return updated
    }

    suspend fun toggleSubscriptionTier() {
        val profile = userDao.getProfileSync() ?: return
        val newIsPro = !profile.isPro
        val newTier = if (newIsPro) "VIP Studio" else "Atelier Standard"
        userDao.updateSubscription("default_user", newIsPro, newTier)
    }

    private fun createSeedWardrobeItems(): List<WardrobeItem> {
        val items = mutableListOf<WardrobeItem>()

        // 16 Upperwear
        items.add(WardrobeItem(name = "Heavy Black Hoodie", category = "Upperwear", subcategory = "Hoodie", primaryColour = "Onyx", styleTags = "Streetwear", material = "Heavyweight French Terry", wearCount = 14, favourite = true))
        items.add(WardrobeItem(name = "Crisp White Tee", category = "Upperwear", subcategory = "T-Shirt", primaryColour = "Ivory", styleTags = "Minimalist", material = "Organic Cotton", wearCount = 22))
        items.add(WardrobeItem(name = "Black Silk Shirt", category = "Upperwear", subcategory = "Shirt", primaryColour = "Silk Black", styleTags = "Evening", material = "Mulberry Silk", wearCount = 4))
        items.add(WardrobeItem(name = "White Poplin Dress Shirt", category = "Upperwear", subcategory = "Shirt", primaryColour = "Ivory", styleTags = "Formal", material = "Poplin Cotton", wearCount = 7))
        items.add(WardrobeItem(name = "Charcoal Cuban Collar", category = "Upperwear", subcategory = "Shirt", primaryColour = "Slate", styleTags = "Relaxed", material = "Matte Linen", wearCount = 5))
        items.add(WardrobeItem(name = "Champagne Knit Polo", category = "Upperwear", subcategory = "Polo", primaryColour = "Champagne", styleTags = "Smart Casual", material = "Merino Wool", wearCount = 8))
        items.add(WardrobeItem(name = "Washed Grey Boxy Tee", category = "Upperwear", subcategory = "T-Shirt", primaryColour = "Slate", styleTags = "Streetwear", material = "240 GSM Cotton", wearCount = 12))
        items.add(WardrobeItem(name = "Oatmeal Waffle Crewneck", category = "Upperwear", subcategory = "Sweater", primaryColour = "Sand", styleTags = "Casual", material = "Waffle Cotton", wearCount = 16))
        items.add(WardrobeItem(name = "Midnight Navy Overshirt", category = "Upperwear", subcategory = "Overshirt", primaryColour = "Onyx", styleTags = "Smart Casual", material = "Heavy Twill", wearCount = 6))
        items.add(WardrobeItem(name = "Textured Taupe Cardigan", category = "Upperwear", subcategory = "Cardigan", primaryColour = "Sand", styleTags = "Minimalist", material = "Cashmere Blend", wearCount = 10))
        items.add(WardrobeItem(name = "Raw Umber Graphic Tee", category = "Upperwear", subcategory = "T-Shirt", primaryColour = "Onyx", styleTags = "Streetwear", material = "Cotton", wearCount = 13))
        items.add(WardrobeItem(name = "Chambray Utility Workshirt", category = "Upperwear", subcategory = "Shirt", primaryColour = "Slate", styleTags = "Casual", material = "Chambray", wearCount = 9))
        items.add(WardrobeItem(name = "Olive Oversized Henley", category = "Upperwear", subcategory = "Long Sleeve", primaryColour = "Slate", styleTags = "Casual", material = "Slub Cotton", wearCount = 4))
        items.add(WardrobeItem(name = "Off-White Mock Neck", category = "Upperwear", subcategory = "Knitwear", primaryColour = "Ivory", styleTags = "Minimalist", material = "Fine Cotton", wearCount = 11))
        items.add(WardrobeItem(name = "Striped Breton Longsleeve", category = "Upperwear", subcategory = "T-Shirt", primaryColour = "Ivory", styleTags = "Casual", material = "Cotton", wearCount = 15))
        items.add(WardrobeItem(name = "Ashen Charcoal Turtleneck", category = "Upperwear", subcategory = "Knitwear", primaryColour = "Onyx", styleTags = "Formal", material = "Merino Wool", wearCount = 3))

        // 12 Bottomwear
        items.add(WardrobeItem(name = "Beige Pleated Trousers", category = "Bottomwear", subcategory = "Trousers", primaryColour = "Sand", styleTags = "Smart Casual", material = "Wool Blend", wearCount = 9, favourite = true))
        items.add(WardrobeItem(name = "Grey Utility Cargo", category = "Bottomwear", subcategory = "Cargo", primaryColour = "Slate", styleTags = "Streetwear", material = "Cotton", wearCount = 11))
        items.add(WardrobeItem(name = "Obsidian Wide Leg Chinos", category = "Bottomwear", subcategory = "Chinos", primaryColour = "Onyx", styleTags = "Minimalist", material = "Cotton Twill", wearCount = 18))
        items.add(WardrobeItem(name = "Faded Blue Relaxed Denim", category = "Bottomwear", subcategory = "Jeans", primaryColour = "Slate", styleTags = "Casual", material = "13oz Denim", wearCount = 24))
        items.add(WardrobeItem(name = "Washed Olive Field Pants", category = "Bottomwear", subcategory = "Trousers", primaryColour = "Slate", styleTags = "Streetwear", material = "Ripstop", wearCount = 7))
        items.add(WardrobeItem(name = "Charcoal Tailored Shorts", category = "Bottomwear", subcategory = "Shorts", primaryColour = "Onyx", styleTags = "Minimalist", material = "Tropical Wool", wearCount = 12))
        items.add(WardrobeItem(name = "Cream Linen Drawstring Pants", category = "Bottomwear", subcategory = "Trousers", primaryColour = "Ivory", styleTags = "Casual", material = "Pure Linen", wearCount = 8))
        items.add(WardrobeItem(name = "Black Heavy Sweatpants", category = "Bottomwear", subcategory = "Joggers", primaryColour = "Onyx", styleTags = "Streetwear", material = "French Terry", wearCount = 20))
        items.add(WardrobeItem(name = "Raw Indigo Selvedge Jeans", category = "Bottomwear", subcategory = "Jeans", primaryColour = "Onyx", styleTags = "Vintage", material = "Raw Denim", wearCount = 14))
        items.add(WardrobeItem(name = "Mocha Flannel Slacks", category = "Bottomwear", subcategory = "Trousers", primaryColour = "Sand", styleTags = "Formal", material = "Wool Flannel", wearCount = 5))
        items.add(WardrobeItem(name = "Slate Grey Track Pants", category = "Bottomwear", subcategory = "Track Pants", primaryColour = "Slate", styleTags = "Sporty", material = "Nylon Blend", wearCount = 16))
        items.add(WardrobeItem(name = "Khaki Pleated Bermudas", category = "Bottomwear", subcategory = "Shorts", primaryColour = "Sand", styleTags = "Casual", material = "Cotton Canvas", wearCount = 6))

        // 5 Outerwear
        items.add(WardrobeItem(name = "Washed Charcoal Boxy Jacket", category = "Outerwear", subcategory = "Jacket", primaryColour = "Onyx", styleTags = "Streetwear", material = "Heavy Canvas", wearCount = 17, favourite = true))
        items.add(WardrobeItem(name = "Tailored Camel Overcoat", category = "Outerwear", subcategory = "Coat", primaryColour = "Sand", styleTags = "Formal", material = "Cashmere Wool", wearCount = 6))
        items.add(WardrobeItem(name = "Minimal Black Bomber", category = "Outerwear", subcategory = "Jacket", primaryColour = "Onyx", styleTags = "Minimalist", material = "Matte Nylon", wearCount = 21))
        items.add(WardrobeItem(name = "Vintage Denim Trucker", category = "Outerwear", subcategory = "Jacket", primaryColour = "Slate", styleTags = "Vintage 90s", material = "Rigid Denim", wearCount = 13))
        items.add(WardrobeItem(name = "Stone Windbreaker Parka", category = "Outerwear", subcategory = "Parka", primaryColour = "Sand", styleTags = "Sporty", material = "Technical Shell", wearCount = 8))

        // 6 Footwear
        items.add(WardrobeItem(name = "Chalk Low Sneakers", category = "Footwear", subcategory = "Sneakers", primaryColour = "Chalk", styleTags = "Versatile", material = "Calfskin", wearCount = 35, favourite = true))
        items.add(WardrobeItem(name = "Brown Leather Loafers", category = "Footwear", subcategory = "Loafers", primaryColour = "Sand", styleTags = "Smart Evening", material = "Italian Calfskin", wearCount = 12))
        items.add(WardrobeItem(name = "Retro Runner Sneakers", category = "Footwear", subcategory = "Sneakers", primaryColour = "Chalk", styleTags = "Streetwear", material = "Suede / Mesh", wearCount = 28))
        items.add(WardrobeItem(name = "Onyx Chelsea Boots", category = "Footwear", subcategory = "Boots", primaryColour = "Onyx", styleTags = "Formal", material = "Polished Leather", wearCount = 10))
        items.add(WardrobeItem(name = "Chalk Minimal Slides", category = "Footwear", subcategory = "Sandals", primaryColour = "Chalk", styleTags = "Casual", material = "Molded EVA", wearCount = 18))
        items.add(WardrobeItem(name = "Suede Desert Boots", category = "Footwear", subcategory = "Boots", primaryColour = "Sand", styleTags = "Smart Casual", material = "Sand Suede", wearCount = 9))

        // 3 Accessories
        items.add(WardrobeItem(name = "Subtle Gold Chrono", category = "Accessories", subcategory = "Watch", primaryColour = "Sand", styleTags = "Luxury Minimal", material = "Gold Tone Steel", wearCount = 28, favourite = true))
        items.add(WardrobeItem(name = "Black Minimalist Cap", category = "Accessories", subcategory = "Cap", primaryColour = "Onyx", styleTags = "Streetwear", material = "Cotton Twill", wearCount = 19))
        items.add(WardrobeItem(name = "Matte Silver Cuff", category = "Accessories", subcategory = "Jewellery", primaryColour = "Slate", styleTags = "Minimalist", material = "Sterling Silver", wearCount = 22))

        return items
    }
}
