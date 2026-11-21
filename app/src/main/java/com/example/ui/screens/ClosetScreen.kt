package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WardrobeItem
import com.example.ui.StylloViewModel
import com.example.ui.components.GarmentThumbnailArt
import com.example.ui.theme.StylloBackground
import com.example.ui.theme.StylloOnPrimary
import com.example.ui.theme.StylloOnPrimaryContainer
import com.example.ui.theme.StylloOnSurface
import com.example.ui.theme.StylloOnSurfaceVariant
import com.example.ui.theme.StylloPrimary
import com.example.ui.theme.StylloPrimaryContainer
import com.example.ui.theme.StylloSurface
import com.example.ui.theme.StylloSurfaceContainer
import com.example.ui.theme.StylloSurfaceHigh
import com.example.ui.theme.StylloSurfaceHighest
import com.example.ui.theme.StylloSurfaceLow
import com.example.ui.theme.StylloSurfaceLowest

@Composable
fun ClosetScreen(
    viewModel: StylloViewModel,
    onNavigateToCreate: () -> Unit
) {
    val filteredItems by viewModel.filteredWardrobeItems.collectAsState()
    val allItems by viewModel.allWardrobeItems.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val showAddDialog by viewModel.showAddItemDialog.collectAsState()

    val categories = listOf(
        "All" to allItems.size,
        "Upperwear" to allItems.count { it.category.equals("Upperwear", true) },
        "Bottomwear" to allItems.count { it.category.equals("Bottomwear", true) },
        "Outerwear" to allItems.count { it.category.equals("Outerwear", true) },
        "Footwear" to allItems.count { it.category.equals("Footwear", true) },
        "Accessories" to allItems.count { it.category.equals("Accessories", true) }
    )

    Box(modifier = Modifier.fillMaxSize().background(StylloBackground)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Utilization Ribbon
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(StylloSurfaceHigh)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Insights,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "78% Wardrobe Utilized • 3 unworn ready for restyling",
                            color = StylloOnSurface,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = StylloPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // 2. Collection Header & Add Item CTA
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "My Wardrobe",
                                color = StylloOnSurface,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(StylloSurfaceHighest)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "${allItems.size} PIECES",
                                    color = StylloPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                        Text(
                            text = "Autumn / Winter Atelier Catalog",
                            color = StylloOnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = { viewModel.openAddItemDialog() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StylloPrimary,
                            contentColor = StylloOnPrimary
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("button_add_item")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "ADD ITEM",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 0.8.sp
                            )
                        }
                    }
                }
            }

            // 3. Search & Filter Bar
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = {
                            Text(
                                text = "Search clothes, fabrics, tones...",
                                color = StylloOnSurfaceVariant.copy(alpha = 0.7f),
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = StylloOnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = StylloOnSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("input_wardrobe_search"),
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = StylloSurfaceLow,
                            unfocusedContainerColor = StylloSurfaceLow,
                            focusedTextColor = StylloOnSurface,
                            unfocusedTextColor = StylloOnSurface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(StylloSurfaceContainer)
                            .clickable { viewModel.showToast("Filter presets active: Autumn/Winter") }
                            .testTag("button_filter_tune"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Tune Filters",
                            tint = StylloOnSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 4. Category Filter Track
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { (cat, count) ->
                        val isSelected = selectedCategory.equals(cat, true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) StylloPrimaryContainer.copy(alpha = 0.25f)
                                    else StylloSurfaceLow
                                )
                                .clickable { viewModel.selectCategory(cat) }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                                .testTag("cat_chip_${cat.lowercase()}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$cat $count",
                                color = if (isSelected) StylloPrimary else StylloOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }

                    // Custom category chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(StylloSurfaceLow)
                            .clickable { viewModel.openAddItemDialog() }
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = StylloPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Custom",
                                color = StylloPrimary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // 5. Garment Cards (2-Column Grid)
            items(filteredItems, key = { it.id }) { item ->
                WardrobeItemCard(
                    item = item,
                    onToggleFavorite = { viewModel.toggleItemFavorite(item) },
                    onDeleteItem = { viewModel.deleteItem(item) }
                )
            }

            // 6. Restyle Bottom Curate Prompt
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(StylloPrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Flare,
                                    contentDescription = null,
                                    tint = StylloPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Ready to Restyle?",
                                    color = StylloOnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = "Generate 5 new outfits from your unworn pieces",
                                    color = StylloOnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Button(
                            onClick = { onNavigateToCreate() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = StylloPrimaryContainer,
                                contentColor = StylloOnPrimaryContainer
                            ),
                            shape = RoundedCornerShape(18.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("button_restyle_curate")
                        ) {
                            Text(
                                text = "Curate",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Add Garment Dialog
        if (showAddDialog) {
            AddGarmentDialog(
                onDismiss = { viewModel.closeAddItemDialog() },
                onAdd = { name, category, subcategory, color, style, material, fit ->
                    viewModel.addNewGarment(name, category, subcategory, color, style, material, fit)
                }
            )
        }
    }
}

@Composable
fun WardrobeItemCard(
    item: WardrobeItem,
    onToggleFavorite: () -> Unit,
    onDeleteItem: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .testTag("wardrobe_card_${item.id}"),
        colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Visual top layer with badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.78f)
            ) {
                GarmentThumbnailArt(
                    category = item.category,
                    primaryColor = item.primaryColour
                )

                // Top Left Color Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(StylloSurfaceLowest.copy(alpha = 0.75f))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(StylloPrimary)
                        )
                        Text(
                            text = item.primaryColour.uppercase(),
                            color = StylloOnSurface,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Top Right Favorite Button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(StylloSurfaceLowest.copy(alpha = 0.65f))
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (item.favourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (item.favourite) StylloPrimary else StylloOnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Bottom Left Wear Cycle Count
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(StylloSurfaceLowest.copy(alpha = 0.85f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = "${item.wearCount}x",
                            color = StylloOnSurface,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Details metadata bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.category.uppercase(),
                        color = StylloOnSurfaceVariant,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )

                    Box {
                        IconButton(
                            onClick = { showMenu = true },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More",
                                tint = StylloOnSurfaceVariant,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(StylloSurfaceContainer)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete", color = StylloPrimary) },
                                onClick = {
                                    showMenu = false
                                    onDeleteItem()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = StylloPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            )
                        }
                    }
                }

                Text(
                    text = item.name,
                    color = StylloOnSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.styleTags,
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(StylloSurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = item.material.take(10),
                            color = StylloOnSurfaceVariant,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddGarmentDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, category: String, subcategory: String, color: String, style: String, material: String, fit: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Upperwear") }
    var color by remember { mutableStateOf("Onyx") }
    var style by remember { mutableStateOf("Streetwear") }
    var material by remember { mutableStateOf("Organic Cotton") }
    var fit by remember { mutableStateOf("Relaxed") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "AI Clothing Analysis",
                color = StylloOnSurface,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Smart Vision will auto-classify garment tags, color tones and silhouette:",
                    color = StylloOnSurfaceVariant,
                    fontSize = 12.sp
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Garment Name") },
                    placeholder = { Text("e.g. Oatmeal Waffle Knit") },
                    modifier = Modifier.fillMaxWidth().testTag("input_add_garment_name"),
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = color,
                        onValueChange = { color = it },
                        label = { Text("Tone") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = style,
                        onValueChange = { style = it },
                        label = { Text("Style") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = material,
                        onValueChange = { material = it },
                        label = { Text("Fabric") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val finalName = if (name.isNotBlank()) name else "New Curated Garment"
                    onAdd(finalName, category, "Atelier Piece", color, style, material, fit)
                },
                colors = ButtonDefaults.buttonColors(containerColor = StylloPrimary, contentColor = StylloOnPrimary),
                modifier = Modifier.testTag("button_confirm_add_garment")
            ) {
                Text("Confirm & Catalog")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = StylloOnSurfaceVariant)
            }
        },
        containerColor = StylloSurfaceContainer
    )
}
