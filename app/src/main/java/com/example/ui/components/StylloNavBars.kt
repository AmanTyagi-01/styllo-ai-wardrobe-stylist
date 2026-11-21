package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.theme.StylloOnSurface
import com.example.ui.theme.StylloOnSurfaceVariant
import com.example.ui.theme.StylloPrimary
import com.example.ui.theme.StylloPrimaryContainer
import com.example.ui.theme.StylloSecondary
import com.example.ui.theme.StylloSurfaceHigh
import com.example.ui.theme.StylloSurfaceLowest

@Composable
fun StylloTopBar(
    subtitle: String = "Curated",
    vipBadge: String = "VIP Studio",
    onProfileClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(StylloSurfaceLowest.copy(alpha = 0.95f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(StylloSurfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Checkroom,
                    contentDescription = "Styllo Logo",
                    tint = StylloPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "SWIPEWARDROBE",
                color = StylloOnSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                letterSpacing = 1.sp
            )
        }

        // Right side: VIP pill & profile avatar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(StylloSurfaceHigh)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
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
                        text = vipBadge,
                        color = StylloSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(StylloPrimaryContainer)
                    .clickable { onProfileClick() }
                    .testTag("profile_avatar_button"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = StylloSurfaceLowest,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StylloBottomBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(StylloSurfaceLowest.copy(alpha = 0.95f))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTab.values().forEach { tab ->
            val isSelected = currentTab == tab
            val icon = when (tab) {
                AppTab.TODAY -> Icons.Default.Checkroom
                AppTab.CLOSET -> Icons.Default.GridView
                AppTab.CREATE -> Icons.Default.AutoAwesome
                AppTab.STYLIST -> Icons.Default.Forum
                AppTab.DNA -> Icons.Default.Person
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(tab) }
                    .testTag("nav_${tab.name.lowercase()}"),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (tab == AppTab.CREATE) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(StylloPrimary.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = tab.title,
                            tint = StylloPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = tab.title,
                        tint = if (isSelected) StylloPrimary else StylloOnSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = tab.title.uppercase(),
                    color = if (isSelected) StylloPrimary else StylloOnSurfaceVariant,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                // Bottom active indicator dot
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(3.dp)
                            .clip(CircleShape)
                            .background(StylloPrimary)
                    )
                }
            }
        }
    }
}
