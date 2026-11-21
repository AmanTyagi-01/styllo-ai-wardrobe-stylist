package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.StylloViewModel
import com.example.ui.components.EditorialHeroArt
import com.example.ui.components.GarmentThumbnailArt
import com.example.ui.theme.StylloBackground
import com.example.ui.theme.StylloEmeraldMatch
import com.example.ui.theme.StylloOnPrimary
import com.example.ui.theme.StylloOnPrimaryContainer
import com.example.ui.theme.StylloOnSurface
import com.example.ui.theme.StylloOnSurfaceVariant
import com.example.ui.theme.StylloPrimary
import com.example.ui.theme.StylloPrimaryContainer
import com.example.ui.theme.StylloSecondary
import com.example.ui.theme.StylloSurface
import com.example.ui.theme.StylloSurfaceContainer
import com.example.ui.theme.StylloSurfaceHigh
import com.example.ui.theme.StylloSurfaceHighest
import com.example.ui.theme.StylloSurfaceLow
import com.example.ui.theme.StylloSurfaceLowest

@Composable
fun TodayScreen(
    viewModel: StylloViewModel,
    onNavigateToCreate: () -> Unit,
    onNavigateToCloset: () -> Unit,
    onNavigateToDna: () -> Unit
) {
    val homeOccasion by viewModel.homeOccasion.collectAsState()
    val allOutfits by viewModel.allOutfits.collectAsState()
    val currentDeckIndex by viewModel.currentDeckIndex.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val wardrobeItems by viewModel.allWardrobeItems.collectAsState()

    val currentFit = allOutfits.getOrNull(currentDeckIndex) ?: allOutfits.firstOrNull()

    val occasions = listOf(
        "Casual", "College", "Office", "Party", "Wedding", "Date", "Travel", "Festival", "Gym"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylloBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Interactive Toast Message Banner
        AnimatedVisibility(visible = toastMessage != null) {
            toastMessage?.let { msg ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(StylloSurfaceHighest)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = msg,
                            color = StylloOnSurface,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(
                        onClick = { viewModel.clearToast() },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss",
                            tint = StylloOnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Header Greeting & Weather Pill
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Good morning, Aman 👋",
                    color = StylloOnSurfaceVariant,
                    fontSize = 14.sp
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(StylloSurfaceHigh)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = StylloSecondary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "VIP Studio",
                            color = StylloSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Text(
                text = "What are you wearing today?",
                color = StylloOnSurface,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                lineHeight = 34.sp
            )

            // Weather Context Pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(StylloSurfaceLow)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = null,
                    tint = StylloPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "22°C • Partly Sunny • Mild Breeze — Perfect for breathable layers",
                    color = StylloOnSurfaceVariant,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Quick Occasion Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            occasions.forEach { occasion ->
                val isSelected = homeOccasion.equals(occasion, ignoreCase = true)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) StylloPrimaryContainer else StylloSurfaceContainer)
                        .clickable { viewModel.setHomeOccasion(occasion) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("chip_occasion_${occasion.lowercase()}"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(StylloOnPrimaryContainer)
                            )
                        }
                        Text(
                            text = occasion,
                            color = if (isSelected) StylloOnPrimaryContainer else StylloOnSurfaceVariant,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Section Title: Today's Curated Fit
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = StylloPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Today's Curated Fit",
                    color = StylloOnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif
                )
            }

            Text(
                text = "LOOK 01 OF 05",
                color = StylloPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Primary Hero Card (Clean Minimalist / Current Curated Fit)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .testTag("hero_curated_fit_card"),
            colors = CardDefaults.cardColors(containerColor = StylloSurfaceHigh)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // 3:4 Aspect Ratio Visual Layer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.85f)
                ) {
                    EditorialHeroArt(
                        modifier = Modifier.fillMaxSize(),
                        styleType = currentFit?.style ?: "Clean Minimal"
                    )

                    // Multi-tier gradient scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.5f),
                                        Color.Transparent,
                                        StylloSurfaceLowest.copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )

                    // Top Floating Match Badge & Save Bookmark
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(StylloSurfaceLowest.copy(alpha = 0.8f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(StylloEmeraldMatch)
                                )
                                Text(
                                    text = "${currentFit?.harmonyScore ?: 96}%",
                                    color = StylloPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = "Match",
                                    color = StylloOnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(StylloSurfaceLowest.copy(alpha = 0.8f))
                                .clickable {
                                    currentFit?.let { viewModel.toggleBookmark(it) }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (currentFit?.isSaved == true) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Save to Capsule",
                                tint = StylloPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Card Bottom In-Image Details Layer
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = currentFit?.title ?: "Clean Minimalist",
                                color = StylloOnSurface,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Serif
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(StylloPrimary.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "BREATHABLE COTTONS",
                                    color = StylloPrimary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }

                        // Garment tags cluster
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf<Pair<String, Color>>(
                                Pair(currentFit?.upperwearName ?: "White Relaxed Tee", StylloPrimary),
                                Pair(currentFit?.bottomwearName ?: "Beige Pleated Trousers", StylloPrimaryContainer),
                                Pair(currentFit?.footwearName ?: "Minimal Leather Lows", StylloSecondary),
                                Pair(currentFit?.accessoryName ?: "Leather Watch", StylloOnSurfaceVariant)
                            ).forEach { (itemName, dotColor) ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(StylloSurfaceLowest.copy(alpha = 0.85f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(5.dp)
                                                .clip(CircleShape)
                                                .background(dotColor)
                                        )
                                        Text(
                                            text = itemName,
                                            color = StylloOnSurface,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Action Bar: Wear This, Try Another, Customize
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(StylloSurfaceHigh)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { currentFit?.let { viewModel.wearCurrentOutfit(it) } },
                        modifier = Modifier
                            .weight(1.3f)
                            .height(46.dp)
                            .testTag("button_wear_this"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StylloPrimaryContainer,
                            contentColor = StylloOnPrimaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Checkroom,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Wear This",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.nextOutfit() },
                        modifier = Modifier
                            .weight(1.2f)
                            .height(46.dp)
                            .testTag("button_try_another"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StylloSurfaceContainer,
                            contentColor = StylloOnSurface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cached,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Try Another",
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(StylloSurfaceContainer)
                            .clickable { onNavigateToCreate() }
                            .testTag("button_customize_fit"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Customize",
                            tint = StylloPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Climate Forecast Note Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(StylloPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Thermostat,
                        contentDescription = null,
                        tint = StylloPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "CLIMATE FORECAST NOTE",
                            color = StylloPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .clip(CircleShape)
                                .background(StylloOnSurfaceVariant)
                        )
                        Text(
                            text = "Optimal Choice",
                            color = StylloOnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        text = "Gentle sun with 18km/h breeze",
                        color = StylloOnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )

                    Text(
                        text = "Midday temperatures hover around 22°C. A 240 GSM organic cotton crewneck combined with breathable pleated linen-blend trousers guarantees thermal balance and moisture resistance all day.",
                        color = StylloOnSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Style DNA Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Your Style DNA",
                    color = StylloOnSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Curated for your profile preferences",
                    color = StylloOnSurfaceVariant,
                    fontSize = 12.sp
                )
            }

            Row(
                modifier = Modifier.clickable { onNavigateToDna() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Explore",
                    color = StylloPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = StylloPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Style DNA 2-Card Grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Modern Street Look Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToDna() },
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        GarmentThumbnailArt(category = "upperwear", primaryColor = "onyx")
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(StylloSurfaceLowest.copy(alpha = 0.85f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "90% Fit",
                                color = StylloPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Modern Street",
                        color = StylloOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Charcoal Layering",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            // Soft Structured Look Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToDna() },
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        GarmentThumbnailArt(category = "bottomwear", primaryColor = "sand")
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(StylloSurfaceLowest.copy(alpha = 0.85f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "84% Fit",
                                color = StylloSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Soft Structured",
                        color = StylloOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Neutral Monotones",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Section: Recent Wardrobe Additions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Checkroom,
                    contentDescription = null,
                    tint = StylloPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Recent Wardrobe Additions",
                    color = StylloOnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif
                )
            }

            Text(
                text = "View All (${wardrobeItems.size})",
                color = StylloOnSurfaceVariant,
                fontSize = 12.sp,
                modifier = Modifier.clickable { onNavigateToCloset() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Wardrobe 3-Card Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val sampleItems = wardrobeItems.take(3)
            sampleItems.forEach { item ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToCloset() },
                    colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.8f)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            GarmentThumbnailArt(
                                category = item.category,
                                primaryColor = item.primaryColour
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(StylloSurfaceLowest.copy(alpha = 0.9f))
                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.category.take(4),
                                    color = StylloOnSurface,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.name,
                            color = StylloOnSurface,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${item.wearCount}x worn",
                            color = if (item.wearCount > 10) StylloPrimary else StylloOnSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
