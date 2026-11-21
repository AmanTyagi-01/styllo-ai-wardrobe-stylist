package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Undo
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Outfit
import com.example.data.model.WardrobeItem
import com.example.ui.StylloViewModel
import com.example.ui.components.EditorialHeroArt
import com.example.ui.components.GarmentThumbnailArt
import com.example.ui.theme.StylloBackground
import com.example.ui.theme.StylloEmeraldMatch
import com.example.ui.theme.StylloError
import com.example.ui.theme.StylloOnPrimary
import com.example.ui.theme.StylloOnSurface
import com.example.ui.theme.StylloOnSurfaceVariant
import com.example.ui.theme.StylloPrimary
import com.example.ui.theme.StylloPrimaryContainer
import com.example.ui.theme.StylloSecondary
import com.example.ui.theme.StylloSecondaryContainer
import com.example.ui.theme.StylloSurface
import com.example.ui.theme.StylloSurfaceContainer
import com.example.ui.theme.StylloSurfaceHigh
import com.example.ui.theme.StylloSurfaceHighest
import com.example.ui.theme.StylloSurfaceLow
import com.example.ui.theme.StylloSurfaceLowest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeDeckScreen(
    viewModel: StylloViewModel
) {
    val allOutfits by viewModel.allOutfits.collectAsState()
    val currentIndex by viewModel.currentDeckIndex.collectAsState()
    val allWardrobeItems by viewModel.allWardrobeItems.collectAsState()
    val swapCategory by viewModel.swapSheetCategory.collectAsState()

    val currentOutfit = allOutfits.getOrNull(currentIndex) ?: allOutfits.firstOrNull()

    // Smooth physics drag offset & rotation
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(StylloBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with contextual tags
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Discover Your Style",
                        color = StylloOnSurface,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "CURATION 08/24",
                        color = StylloPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "Swipe through looks crafted exclusively from your wardrobe.",
                    color = StylloOnSurfaceVariant,
                    fontSize = 12.sp
                )

                // Filter Pill Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(StylloPrimaryContainer.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = StylloPrimary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "${currentOutfit?.occasion ?: "Wedding"} • ${currentOutfit?.style ?: "Trendy"}",
                                color = StylloPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "• 42 items",
                                color = StylloOnSurfaceVariant,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(StylloSurfaceHigh)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                tint = StylloOnSurfaceVariant,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "Tuned by AI",
                                color = StylloOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(StylloSurfaceHigh)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = StylloOnSurfaceVariant,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "Warm tones",
                                color = StylloOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Central 3D Swipe Deck Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Layer 3 (Deepest)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .aspectRatio(0.72f)
                        .scale(0.90f)
                        .offset(y = 20.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(StylloSurfaceLowest.copy(alpha = 0.5f))
                )

                // Layer 2 (Middle)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.94f)
                        .aspectRatio(0.72f)
                        .scale(0.95f)
                        .offset(y = 10.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(StylloSurfaceLow)
                )

                // Layer 1 (Interactive Front Card)
                val currentDragX = offsetX.value
                val rotationDeg = currentDragX * 0.04f

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.72f)
                        .offset { IntOffset(currentDragX.roundToInt(), 0) }
                        .rotate(rotationDeg)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    scope.launch {
                                        if (offsetX.value > 250f) {
                                            currentOutfit?.let { viewModel.onSwipeRight(it) }
                                            offsetX.snapTo(0f)
                                        } else if (offsetX.value < -250f) {
                                            currentOutfit?.let { viewModel.onSwipeLeft(it) }
                                            offsetX.snapTo(0f)
                                        } else {
                                            offsetX.animateTo(0f, tween(300))
                                        }
                                    }
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    scope.launch {
                                        offsetX.snapTo(offsetX.value + dragAmount.x)
                                    }
                                }
                            )
                        }
                        .testTag("interactive_swipe_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = StylloSurfaceHigh)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Background Art Canvas
                        EditorialHeroArt(
                            modifier = Modifier.fillMaxSize(),
                            styleType = currentOutfit?.style ?: "Clean Minimal"
                        )

                        // Vignette and dark bottom scrim
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            StylloSurfaceLowest.copy(alpha = 0.7f),
                                            Color.Transparent,
                                            StylloSurfaceLowest.copy(alpha = 0.98f)
                                        )
                                    )
                                )
                        )

                        // Like Overlay Stamp (Drag Right)
                        if (currentDragX > 40f) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 60.dp, end = 20.dp)
                                    .rotate(15f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(StylloPrimary.copy(alpha = 0.9f))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "SAVED TO LOOKBOOK",
                                    color = StylloLowestText(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        // Pass Overlay Stamp (Drag Left)
                        if (currentDragX < -40f) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(top = 60.dp, start = 20.dp)
                                    .rotate(-15f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(StylloError.copy(alpha = 0.9f))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "NEXT LOOK",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        // Top Badges (Harmony & Occasion)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(StylloSurfaceLowest.copy(alpha = 0.85f))
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(StylloEmeraldMatch)
                                        )
                                        Text(
                                            text = "${currentOutfit?.harmonyScore ?: 94}%",
                                            color = StylloEmeraldMatch,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "HARMONY",
                                            color = StylloOnSurfaceVariant,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.8.sp
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(StylloSurfaceLowest.copy(alpha = 0.75f))
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = "LOOK ${currentIndex + 1} • ${(currentOutfit?.occasion ?: "WEDDING").uppercase()}",
                                        color = StylloPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            // AI Style Note Pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(StylloSurfaceHigh.copy(alpha = 0.85f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Psychology,
                                        contentDescription = null,
                                        tint = StylloPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "Tailored neutrals from your saved preferences",
                                        color = StylloOnSurface,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        // Bottom Overlay: Outfit details, item triggers, rationale
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column {
                                    Text(
                                        text = currentOutfit?.title ?: "Tailored Silk & Pleat",
                                        color = StylloOnSurface,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Serif
                                    )
                                    Text(
                                        text = "Smart Evening Reception • 4 Closet Items",
                                        color = StylloPrimary,
                                        fontSize = 11.sp
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(StylloPrimary.copy(alpha = 0.2f))
                                        .clickable { viewModel.openSwapSheet("Upperwear") }
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Text(
                                            text = "SWAP RACK",
                                            color = StylloPrimary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ExpandLess,
                                            contentDescription = null,
                                            tint = StylloPrimary,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                }
                            }

                            // 4 Tactile Item Trigger Tags (Tap to swap garment)
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    GarmentTriggerTag(
                                        category = "Upperwear",
                                        name = currentOutfit?.upperwearName ?: "Black Silk Shirt",
                                        modifier = Modifier.weight(1f),
                                        onClick = { viewModel.openSwapSheet("Upperwear") }
                                    )
                                    GarmentTriggerTag(
                                        category = "Bottomwear",
                                        name = currentOutfit?.bottomwearName ?: "Beige Pleated Trousers",
                                        modifier = Modifier.weight(1f),
                                        onClick = { viewModel.openSwapSheet("Bottomwear") }
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    GarmentTriggerTag(
                                        category = "Footwear",
                                        name = currentOutfit?.footwearName ?: "Brown Leather Loafers",
                                        modifier = Modifier.weight(1f),
                                        onClick = { viewModel.openSwapSheet("Footwear") }
                                    )
                                    GarmentTriggerTag(
                                        category = "Accessory",
                                        name = currentOutfit?.accessoryName ?: "Subtle Gold Chrono",
                                        modifier = Modifier.weight(1f),
                                        onClick = { viewModel.openSwapSheet("Accessories") }
                                    )
                                }
                            }

                            // Stylist Rationale Micro-Callout
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(StylloSurfaceLowest.copy(alpha = 0.8f))
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                    contentDescription = null,
                                    tint = StylloPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = currentOutfit?.stylistRationale ?: "The contrast of rich black silk against crisp ivory pleats creates effortless presence without feeling overly formal.",
                                    color = StylloOnSurfaceVariant,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // Ergonomic Thumb Action Controls Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Undo / Rewind
                IconButton(
                    onClick = { viewModel.previousOutfit() },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(StylloSurfaceHigh)
                        .testTag("btn_undo_swipe")
                ) {
                    Icon(
                        imageVector = Icons.Default.Undo,
                        contentDescription = "Undo",
                        tint = StylloOnSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Pass / Reject (X)
                IconButton(
                    onClick = { currentOutfit?.let { viewModel.onSwipeLeft(it) } },
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(StylloSurfaceHigh)
                        .testTag("btn_pass_swipe")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Pass Look",
                        tint = StylloError,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Remix / AI Variant
                IconButton(
                    onClick = {
                        viewModel.showToast("Remixing variant silhouettes with current palette ✨")
                        viewModel.nextOutfit()
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(StylloSecondaryContainer)
                        .testTag("btn_remix_swipe")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoFixHigh,
                        contentDescription = "Remix",
                        tint = StylloSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Bookmark / Save Look
                IconButton(
                    onClick = { currentOutfit?.let { viewModel.toggleBookmark(it) } },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(StylloSurfaceHigh)
                        .testTag("btn_bookmark_swipe")
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Bookmark",
                        tint = StylloPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Love / Select Look
                IconButton(
                    onClick = { currentOutfit?.let { viewModel.onSwipeRight(it) } },
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(StylloPrimary)
                        .testTag("btn_love_swipe")
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Love Look",
                        tint = StylloOnPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }

        // Garment Swap Studio Bottom Sheet
        AnimatedVisibility(
            visible = swapCategory != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            val category = swapCategory ?: "Upperwear"
            val candidates = allWardrobeItems.filter {
                it.category.equals(category, true) || it.category.equals("Upperwear", true)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Drag handle
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(42.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(StylloSurfaceHighest)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "ITEM SWAP STUDIO • ${category.uppercase()}",
                                color = StylloPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Change $category",
                                color = StylloOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        }

                        IconButton(
                            onClick = { viewModel.closeSwapSheet() },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = StylloOnSurface
                            )
                        }
                    }

                    Text(
                        text = "Swap pieces seamlessly without rebuilding your look. Smart AI recalculates ensemble cohesion in real time.",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp
                    )

                    // Rack of Alternatives Carousel
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        candidates.take(6).forEachIndexed { idx, candidate ->
                            val matchPercentage = (96 - (idx * 3)).coerceIn(84, 98)
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .clickable {
                                        currentOutfit?.let { outfit ->
                                            viewModel.applyGarmentSwap(outfit, category, candidate)
                                        }
                                    }
                                    .testTag("swap_candidate_${candidate.id}"),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(84.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    ) {
                                        GarmentThumbnailArt(
                                            category = candidate.category,
                                            primaryColor = candidate.primaryColour
                                        )

                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(StylloPrimary.copy(alpha = 0.2f))
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "$matchPercentage% MATCH",
                                                color = StylloPrimary,
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = candidate.name,
                                        color = StylloOnSurface,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${candidate.primaryColour} • ${candidate.styleTags}",
                                        color = StylloOnSurfaceVariant,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }
                    }

                    // Bottom Confirm Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tap piece to instant-preview",
                            color = StylloOnSurfaceVariant,
                            fontSize = 11.sp
                        )

                        Button(
                            onClick = { viewModel.closeSwapSheet() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = StylloPrimary,
                                contentColor = StylloOnPrimary
                            ),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text("Keep Changes", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GarmentTriggerTag(
    category: String,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(StylloSurfaceHigh.copy(alpha = 0.9f))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("tag_swap_${category.lowercase()}"),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.uppercase(),
                    color = StylloPrimary,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = name,
                    color = StylloOnSurface,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = "Swap $category",
                tint = StylloOnSurfaceVariant,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
private fun StylloLowestText(): Color = StylloSurfaceLowest
