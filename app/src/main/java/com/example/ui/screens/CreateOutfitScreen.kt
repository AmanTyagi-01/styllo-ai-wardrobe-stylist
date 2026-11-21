package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StylloViewModel
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
fun CreateOutfitScreen(
    viewModel: StylloViewModel,
    onNavigateToDeck: () -> Unit
) {
    val occasion by viewModel.createOccasion.collectAsState()
    val selectedVibes by viewModel.selectedVibes.collectAsState()
    val weatherSync by viewModel.weatherSync.collectAsState()
    val antiRepeat by viewModel.antiRepeat.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val wardrobeCount by viewModel.wardrobeCount.collectAsState()

    val occasions = listOf(
        "Casual" to "Weekend coffee & daytime chill",
        "Wedding" to "Celebration & evening formal",
        "Campus" to "Lectures, studio & movement",
        "Office" to "Modern business casual",
        "Night Out" to "Dinner, drinks & twilight",
        "Date Night" to "Romantic & dialed-in",
        "Travel" to "Effortless airport transit",
        "Activewear" to "Mobility & morning wellness",
        "Festival" to "Bold curated streetwear",
        "Custom" to "Specify custom dress code"
    )

    val vibes = listOf(
        "Streetwear", "Clean Minimal", "Smart Casual", "Luxury Classic", "Vintage 90s", "Relaxed Cozy"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylloBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Step progress header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "STEP 1 OF 2",
                    color = StylloPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "CURATION CRITERIA",
                    color = StylloOnSurfaceVariant,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }

            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = StylloPrimary,
                trackColor = StylloSurfaceHighest
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Create an Outfit",
                color = StylloOnSurface,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Choose an occasion and vibe. AI will craft bespoke looks strictly from your $wardrobeCount wardrobe items.",
                color = StylloOnSurfaceVariant,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        // Section 1: Occasion Grid (2 columns)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "SELECT OCCASION",
                color = StylloPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                occasions.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { (occ, desc) ->
                            val isSelected = occasion.equals(occ, true)
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.selectCreateOccasion(occ) }
                                    .testTag("create_occasion_${occ.lowercase()}"),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) StylloPrimaryContainer.copy(alpha = 0.18f) else StylloSurfaceLow
                                ),
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, StylloPrimary) else null
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = occ,
                                            color = if (isSelected) StylloPrimary else StylloOnSurface,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = FontFamily.Serif
                                        )

                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clip(CircleShape)
                                                    .background(StylloPrimary),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = StylloSurfaceLowest,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                            }
                                        } else if (occ == "Custom") {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = null,
                                                tint = StylloOnSurfaceVariant,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = desc,
                                        color = StylloOnSurfaceVariant,
                                        fontSize = 10.sp,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Section 2: Aesthetic Mood
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "AESTHETIC MOOD",
                color = StylloPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                vibes.forEach { vibe ->
                    val isSelected = selectedVibes.contains(vibe)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) StylloPrimaryContainer else StylloSurfaceLow)
                            .clickable { viewModel.toggleVibe(vibe) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("vibe_chip_${vibe.lowercase().replace(" ", "_")}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = vibe,
                            color = if (isSelected) StylloOnPrimaryContainer else StylloOnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Section 3: Recommended Palette
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Recommended Palette",
                    color = StylloOnSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Champagne Gold, Charcoal Slate, Heather Grey",
                    color = StylloOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(Color(0xFFF1D5B0)))
                Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(Color(0xFF2E2D30)))
                Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(Color(0xFF8A9099)))
                Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(Color(0xFFD4C5B0)))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Section 4: Fine-tune Constraints
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "FINE-TUNE CONSTRAINTS",
                color = StylloPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            // Must Include Garment Lock
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Column {
                            Text(
                                text = "Must Include (Garment Lock)",
                                color = StylloOnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Heavyweight Oversized Hoodie",
                                color = StylloPrimary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Text(
                        text = "Change",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp,
                        modifier = Modifier.clickable { viewModel.showToast("Selected item locked into recommendations") }
                    )
                }
            }

            // Weather Sync Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cloud,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Column {
                            Text(
                                text = "Local Weather Sync",
                                color = StylloOnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "22°C Mild & Breezy • Active",
                                color = StylloEmeraldMatch,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = weatherSync,
                        onCheckedChange = { viewModel.toggleWeatherSync() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = StylloPrimary,
                            checkedTrackColor = StylloPrimaryContainer
                        )
                    )
                }
            }

            // Anti-Repeat Memory Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Column {
                            Text(
                                text = "Anti-Repeat Memory",
                                color = StylloOnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Skip fits worn within 14 days",
                                color = StylloOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = antiRepeat,
                        onCheckedChange = { viewModel.toggleAntiRepeat() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = StylloPrimary,
                            checkedTrackColor = StylloPrimaryContainer
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Wardrobe Status Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(StylloEmeraldMatch.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "37",
                        color = StylloEmeraldMatch,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column {
                    Text(
                        text = "37 Eligible Items Found",
                        color = StylloOnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "5 items excluded due to temperature & locks",
                        color = StylloOnSurfaceVariant,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Primary Sticky CTA Button
        Button(
            onClick = {
                viewModel.generateOutfitsFromCriteria()
                onNavigateToDeck()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp)
                .testTag("button_generate_ai_looks"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StylloPrimary,
                contentColor = StylloOnPrimary
            ),
            shape = RoundedCornerShape(14.dp),
            enabled = !isGenerating
        ) {
            if (isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = StylloOnPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "GENERATE AI LOOKS",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
