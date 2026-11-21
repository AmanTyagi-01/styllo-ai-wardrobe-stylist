package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Outfit
import com.example.data.model.UserProfile
import com.example.data.model.WardrobeItem
import com.example.data.repository.StylloRepository
import com.example.engine.StylingRecommendationEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppTab(val title: String) {
    TODAY("Today"),
    CLOSET("Closet"),
    CREATE("Create"),
    STYLIST("Stylist"),
    DNA("DNA")
}

class StylloViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StylloRepository(application)

    val allWardrobeItems = repository.allWardrobeItems.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val allOutfits = repository.allOutfits.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val savedOutfits = repository.savedOutfits.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val wornHistory = repository.wornHistory.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val userProfile = repository.userProfile.stateIn(
        viewModelScope, SharingStarted.Lazily, null
    )

    val wardrobeCount = repository.wardrobeCount.stateIn(
        viewModelScope, SharingStarted.Lazily, 42
    )

    // Navigation State
    private val _currentTab = MutableStateFlow(AppTab.TODAY)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // Feedback Toast Notification
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    // Active Home Occasion filter
    private val _homeOccasion = MutableStateFlow("Casual")
    val homeOccasion: StateFlow<String> = _homeOccasion.asStateFlow()

    fun setHomeOccasion(occasion: String) {
        _homeOccasion.value = occasion
        showToast("Switched recommendation context to: $occasion")
    }

    // Swipe Deck state
    private val _currentDeckIndex = MutableStateFlow(0)
    val currentDeckIndex: StateFlow<Int> = _currentDeckIndex.asStateFlow()

    fun nextOutfit() {
        val total = allOutfits.value.size
        if (total > 0) {
            _currentDeckIndex.value = (_currentDeckIndex.value + 1) % total
        }
    }

    fun previousOutfit() {
        val total = allOutfits.value.size
        if (total > 0) {
            _currentDeckIndex.value = (_currentDeckIndex.value - 1 + total) % total
        }
    }

    fun onSwipeRight(outfit: Outfit) {
        viewModelScope.launch {
            repository.recordOutfitFeedback(outfit.id, liked = true)
            repository.toggleSaveOutfit(outfit.id, currentVal = false)
            showToast("Saved to Personal Capsule Collections ✨")
            nextOutfit()
        }
    }

    fun onSwipeLeft(outfit: Outfit) {
        viewModelScope.launch {
            repository.recordOutfitFeedback(outfit.id, liked = false)
            showToast("Passing on look. Tuned recommendations refreshed.")
            nextOutfit()
        }
    }

    fun toggleBookmark(outfit: Outfit) {
        viewModelScope.launch {
            repository.toggleSaveOutfit(outfit.id, outfit.isSaved)
            if (!outfit.isSaved) {
                showToast("Saved to your Personal Capsule Collections")
            } else {
                showToast("Removed from your lookbook vault")
            }
        }
    }

    fun wearCurrentOutfit(outfit: Outfit) {
        viewModelScope.launch {
            repository.markOutfitWorn(outfit)
            showToast("Outfit logged! Have an effortless day, Aman ✨")
        }
    }

    // Wardrobe Search & Category Filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    val filteredWardrobeItems: StateFlow<List<WardrobeItem>> = combine(
        allWardrobeItems,
        searchQuery,
        selectedCategory
    ) { items, query, category ->
        items.filter { item ->
            val matchesCategory = category == "All" || item.category.equals(category, ignoreCase = true)
            val matchesQuery = query.isEmpty() ||
                    item.name.contains(query, ignoreCase = true) ||
                    item.primaryColour.contains(query, ignoreCase = true) ||
                    item.material.contains(query, ignoreCase = true) ||
                    item.styleTags.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleItemFavorite(item: WardrobeItem) {
        viewModelScope.launch {
            repository.toggleFavoriteItem(item.id, item.favourite)
        }
    }

    fun deleteItem(item: WardrobeItem) {
        viewModelScope.launch {
            repository.deleteWardrobeItem(item)
            showToast("Garment removed from catalog")
        }
    }

    // Create Outfit Flow States
    private val _createOccasion = MutableStateFlow("Casual")
    val createOccasion: StateFlow<String> = _createOccasion.asStateFlow()

    private val _selectedVibes = MutableStateFlow(setOf("Streetwear", "Clean Minimal"))
    val selectedVibes: StateFlow<Set<String>> = _selectedVibes.asStateFlow()

    private val _weatherSync = MutableStateFlow(true)
    val weatherSync: StateFlow<Boolean> = _weatherSync.asStateFlow()

    private val _antiRepeat = MutableStateFlow(true)
    val antiRepeat: StateFlow<Boolean> = _antiRepeat.asStateFlow()

    private val _mustIncludeLock = MutableStateFlow<String?>("Heavy Black Hoodie")
    val mustIncludeLock: StateFlow<String?> = _mustIncludeLock.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    fun selectCreateOccasion(occasion: String) {
        _createOccasion.value = occasion
    }

    fun toggleVibe(vibe: String) {
        val current = _selectedVibes.value.toMutableSet()
        if (current.contains(vibe)) {
            if (current.size > 1) current.remove(vibe)
        } else {
            current.add(vibe)
        }
        _selectedVibes.value = current
    }

    fun toggleWeatherSync() {
        _weatherSync.value = !_weatherSync.value
    }

    fun toggleAntiRepeat() {
        _antiRepeat.value = !_antiRepeat.value
    }

    fun generateOutfitsFromCriteria() {
        viewModelScope.launch {
            _isGenerating.value = true
            val criteria = StylingRecommendationEngine.GenerationCriteria(
                occasion = _createOccasion.value,
                vibes = _selectedVibes.value.toList(),
                weatherSync = _weatherSync.value,
                antiRepeat = _antiRepeat.value
            )
            repository.generateNewOutfits(criteria)
            _isGenerating.value = false
            _currentDeckIndex.value = 0
            _currentTab.value = AppTab.CREATE
            showToast("Curated new looks for ${_createOccasion.value}!")
        }
    }

    // Garment Swap Studio Sheet State
    private val _swapSheetCategory = MutableStateFlow<String?>(null)
    val swapSheetCategory: StateFlow<String?> = _swapSheetCategory.asStateFlow()

    fun openSwapSheet(category: String) {
        _swapSheetCategory.value = category
    }

    fun closeSwapSheet() {
        _swapSheetCategory.value = null
    }

    fun applyGarmentSwap(outfit: Outfit, category: String, newItem: WardrobeItem) {
        viewModelScope.launch {
            val updated = repository.swapOutfitGarment(outfit, category, newItem)
            closeSwapSheet()
            showToast("Swapped to ${newItem.name} (${updated.harmonyScore}% Harmony)")
        }
    }

    // Add Garment Item with AI Classification
    private val _showAddItemDialog = MutableStateFlow(false)
    val showAddItemDialog: StateFlow<Boolean> = _showAddItemDialog.asStateFlow()

    fun openAddItemDialog() {
        _showAddItemDialog.value = true
    }

    fun closeAddItemDialog() {
        _showAddItemDialog.value = false
    }

    fun addNewGarment(
        name: String,
        category: String,
        subcategory: String,
        primaryColour: String,
        styleTags: String,
        material: String,
        fit: String
    ) {
        viewModelScope.launch {
            val newItem = WardrobeItem(
                id = UUID.randomUUID().toString(),
                name = name,
                category = category,
                subcategory = subcategory,
                primaryColour = primaryColour,
                styleTags = styleTags,
                material = material,
                fit = fit,
                wearCount = 0,
                favourite = false
            )
            repository.addWardrobeItem(newItem)
            closeAddItemDialog()
            showToast("Added $name to Atelier catalog ✨")
        }
    }

    fun toggleSubscription() {
        viewModelScope.launch {
            repository.toggleSubscriptionTier()
            val pro = userProfile.value?.isPro == true
            if (!pro) {
                showToast("Upgraded to VIP Studio Unlimited ✨")
            } else {
                showToast("Switched to Atelier Standard")
            }
        }
    }
}
