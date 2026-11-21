package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.theme.StylloSurfaceContainer
import com.example.ui.theme.StylloSurfaceHigh
import com.example.ui.theme.StylloSurfaceHighest
import com.example.ui.theme.StylloSurfaceLow
import com.example.ui.theme.StylloSurfaceLowest

data class ChatMessage(val text: String, val isFromUser: Boolean)

@Composable
fun StylistDnaScreen(
    viewModel: StylloViewModel
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val wornHistory by viewModel.wornHistory.collectAsState()

    var chatInput by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hello Aman! I'm your Styllo AI Fashion Director. How can I refine your look today?", false),
            ChatMessage("What jacket should I pair with the Beige Pleated Trousers for an evening event?", true),
            ChatMessage("Pair them with the Washed Charcoal Boxy Jacket or Minimal Black Bomber. The contrast of structured dark outerwear grounded by sand pleats yields effortless Parisian minimalism.", false)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylloBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Section 1: User Profile Header & VIP Studio Tier
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Aman's Style DNA",
                        color = StylloOnSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Atelier Member • Paris / New Delhi Curation",
                        color = StylloOnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(StylloPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = StylloOnPrimaryContainer,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // VIP Studio Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceHigh)
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
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(StylloPrimary.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = StylloPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = userProfile?.vipTier ?: "VIP Studio",
                                    color = StylloPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(StylloEmeraldMatch.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "ACTIVE",
                                        color = StylloEmeraldMatch,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Unlimited AI look generations & climate sync",
                                color = StylloOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.toggleSubscription() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StylloPrimaryContainer,
                            contentColor = StylloOnPrimaryContainer
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("button_manage_vip")
                    ) {
                        Text(text = "Manage", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Section 2: Style DNA Archetype Percentages
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "STYLE ARCHETYPE AFFINITY",
                color = StylloPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StyleAffinityBar(label = "Modern Streetwear", percent = 90, color = StylloPrimary)
                    StyleAffinityBar(label = "Soft Structured / Casual", percent = 84, color = StylloSecondary)
                    StyleAffinityBar(label = "Clean Minimalist", percent = 72, color = StylloPrimaryContainer)
                    StyleAffinityBar(label = "Formal / Tailored Evening", percent = 38, color = StylloOnSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 3: Palette & Fit Preferences
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Palette Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ColorLens,
                            contentDescription = null,
                            tint = StylloPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Tone Palette",
                            color = StylloOnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "Onyx, Ivory, Sand, Slate",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp
                    )

                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(14.dp).clip(CircleShape).background(Color(0xFF131315)))
                        Box(modifier = Modifier.size(14.dp).clip(CircleShape).background(Color(0xFFEFECE5)))
                        Box(modifier = Modifier.size(14.dp).clip(CircleShape).background(Color(0xFFD4C5B0)))
                        Box(modifier = Modifier.size(14.dp).clip(CircleShape).background(Color(0xFF5A6069)))
                    }
                }
            }

            // Silhouette / Fit Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Straighten,
                            contentDescription = null,
                            tint = StylloSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Fit Drape",
                            color = StylloOnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "Relaxed, Oversized",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp
                    )

                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(StylloSurfaceContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = "240 GSM", color = StylloPrimary, fontSize = 9.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(StylloSurfaceContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Pleated", color = StylloSecondary, fontSize = 9.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 4: Wardrobe Utilization Gauge
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = StylloSurfaceContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(StylloEmeraldMatch.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "78%",
                        color = StylloEmeraldMatch,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Wardrobe Utilization Rate",
                        color = StylloOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "33 of 42 pieces worn in current 30-day rotation cycle. 9 pieces eligible for novel combinations.",
                        color = StylloOnSurfaceVariant,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Section 5: AI Stylist Live Consultation
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = null,
                    tint = StylloPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Personal AI Stylist Consultation",
                    color = StylloOnSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif
                )
            }

            // Quick Prompt Ideas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "Style the Beige Pleated Trousers",
                    "Rooftop date outfit recommendation",
                    "How to layer Black Silk Shirt"
                ).forEach { prompt ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(StylloSurfaceLow)
                            .clickable {
                                messages.add(ChatMessage(prompt, true))
                                val reply = when {
                                    prompt.contains("Pleated", true) ->
                                        "For your Beige Pleated Trousers, team them with your Crisp White Tee or Off-White Mock Neck tucked in, finished with the Chalk Low Sneakers for an architectural casual look."
                                    prompt.contains("date", true) ->
                                        "For a rooftop date at 22°C, wear the Black Silk Shirt left slightly open over an ivory tank, paired with the Beige Pleated Trousers and Italian Leather Loafers."
                                    else ->
                                        "Layer the Black Silk Shirt over your Washed Grey Boxy Tee, buttoned halfway, anchored with your Obsidian Wide Leg Chinos."
                                }
                                messages.add(ChatMessage(reply, false))
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = prompt,
                            color = StylloPrimary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Chat Messages Stream
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(StylloSurfaceLow)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                messages.forEach { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.isFromUser) Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 14.dp,
                                        bottomStart = if (msg.isFromUser) 14.dp else 4.dp,
                                        bottomEnd = if (msg.isFromUser) 4.dp else 14.dp
                                    )
                                )
                                .background(if (msg.isFromUser) StylloPrimaryContainer else StylloSurfaceContainer)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = msg.text,
                                color = if (msg.isFromUser) StylloOnPrimaryContainer else StylloOnSurface,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }

                // Chat Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = chatInput,
                        onValueChange = { chatInput = it },
                        placeholder = { Text("Ask your stylist anything...", fontSize = 12.sp, color = StylloOnSurfaceVariant) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("input_stylist_chat"),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = StylloSurfaceLowest,
                            unfocusedContainerColor = StylloSurfaceLowest,
                            focusedTextColor = StylloOnSurface,
                            unfocusedTextColor = StylloOnSurface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )

                    IconButton(
                        onClick = {
                            if (chatInput.isNotBlank()) {
                                val userQ = chatInput
                                messages.add(ChatMessage(userQ, true))
                                chatInput = ""
                                messages.add(
                                    ChatMessage("Based on your 42-piece wardrobe and 22°C breeze, I recommend pairing clean tonal layers—contrasting heavy textures like your Heavy Black Hoodie against breezy pleated trousers for balanced proportion.", false)
                                )
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(StylloPrimary)
                            .testTag("button_send_chat")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = StylloOnPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Section 6: Wear History Log
        if (wornHistory.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "RECENT WEAR HISTORY",
                    color = StylloPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                wornHistory.take(4).forEach { outfit ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = StylloSurfaceLow)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
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
                                    tint = StylloEmeraldMatch,
                                    modifier = Modifier.size(16.dp)
                                )
                                Column {
                                    Text(
                                        text = outfit.title,
                                        color = StylloOnSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "${outfit.upperwearName} • ${outfit.bottomwearName}",
                                        color = StylloOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Text(
                                text = "Worn Today",
                                color = StylloPrimary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StyleAffinityBar(
    label: String,
    percent: Int,
    color: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = StylloOnSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "$percent% Fit",
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LinearProgressIndicator(
            progress = { percent / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = StylloSurfaceHighest
        )
    }
}
