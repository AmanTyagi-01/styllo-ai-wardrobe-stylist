package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.StylloPrimary
import com.example.ui.theme.StylloSecondary
import com.example.ui.theme.StylloSurfaceContainer
import com.example.ui.theme.StylloSurfaceHigh
import com.example.ui.theme.StylloSurfaceLowest

@Composable
fun EditorialHeroArt(
    modifier: Modifier = Modifier,
    styleType: String = "Minimal"
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2C2A28),
                        Color(0xFF1E1D1F),
                        StylloSurfaceLowest
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Atmospheric soft ambient glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(StylloPrimary.copy(alpha = 0.22f), Color.Transparent),
                    center = Offset(w * 0.5f, h * 0.35f),
                    radius = w * 0.65f
                )
            )

            // Stylized high-fashion silhouette
            val p = Path().apply {
                // Head / neck
                moveTo(w * 0.5f, h * 0.16f)
                // Shoulders
                lineTo(w * 0.68f, h * 0.24f)
                // Relaxed white tee drape
                lineTo(w * 0.65f, h * 0.44f)
                lineTo(w * 0.59f, h * 0.47f)
                // Pleated trousers down
                lineTo(w * 0.62f, h * 0.82f)
                lineTo(w * 0.54f, h * 0.84f)
                lineTo(w * 0.51f, h * 0.54f)
                lineTo(w * 0.48f, h * 0.54f)
                lineTo(w * 0.45f, h * 0.84f)
                lineTo(w * 0.38f, h * 0.82f)
                // Left tee drape
                lineTo(w * 0.41f, h * 0.47f)
                lineTo(w * 0.35f, h * 0.44f)
                lineTo(w * 0.32f, h * 0.24f)
                close()
            }

            drawPath(
                path = p,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8E5DD),
                        Color(0xFFD4C5B0),
                        Color(0xFFC2B299)
                    ),
                    startY = h * 0.18f,
                    endY = h * 0.85f
                )
            )

            // Minimalist belt line & pleat accents
            drawLine(
                color = StylloPrimary.copy(alpha = 0.7f),
                start = Offset(w * 0.42f, h * 0.47f),
                end = Offset(w * 0.58f, h * 0.47f),
                strokeWidth = 3.5f
            )

            drawLine(
                color = Color(0xFF9E8A72).copy(alpha = 0.6f),
                start = Offset(w * 0.44f, h * 0.50f),
                end = Offset(w * 0.42f, h * 0.78f),
                strokeWidth = 2f
            )
            drawLine(
                color = Color(0xFF9E8A72).copy(alpha = 0.6f),
                start = Offset(w * 0.56f, h * 0.50f),
                end = Offset(w * 0.58f, h * 0.78f),
                strokeWidth = 2f
            )
        }
    }
}

@Composable
fun GarmentThumbnailArt(
    category: String,
    primaryColor: String,
    modifier: Modifier = Modifier
) {
    val baseBg = when (primaryColor.lowercase()) {
        "onyx", "silk black" -> Color(0xFF1B1B1E)
        "ivory", "chalk" -> Color(0xFF2E2D30)
        "sand", "champagne" -> Color(0xFF2C2720)
        "slate" -> Color(0xFF232529)
        else -> StylloSurfaceContainer
    }

    val accentColor = when (primaryColor.lowercase()) {
        "onyx", "silk black" -> Color(0xFF3B393D)
        "ivory", "chalk" -> Color(0xFFEFECE5)
        "sand", "champagne" -> StylloPrimary
        "slate" -> Color(0xFF8A9099)
        else -> StylloSecondary
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(baseBg)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            when (category.lowercase()) {
                "upperwear" -> {
                    val path = Path().apply {
                        moveTo(w * 0.35f, h * 0.2f)
                        lineTo(w * 0.5f, h * 0.28f)
                        lineTo(w * 0.65f, h * 0.2f)
                        lineTo(w * 0.85f, h * 0.38f)
                        lineTo(w * 0.75f, h * 0.48f)
                        lineTo(w * 0.68f, h * 0.42f)
                        lineTo(w * 0.68f, h * 0.82f)
                        lineTo(w * 0.32f, h * 0.82f)
                        lineTo(w * 0.32f, h * 0.42f)
                        lineTo(w * 0.25f, h * 0.48f)
                        lineTo(w * 0.15f, h * 0.38f)
                        close()
                    }
                    drawPath(path, accentColor)
                }
                "bottomwear" -> {
                    val path = Path().apply {
                        moveTo(w * 0.32f, h * 0.22f)
                        lineTo(w * 0.68f, h * 0.22f)
                        lineTo(w * 0.74f, h * 0.85f)
                        lineTo(w * 0.55f, h * 0.85f)
                        lineTo(w * 0.5f, h * 0.42f)
                        lineTo(w * 0.45f, h * 0.85f)
                        lineTo(w * 0.26f, h * 0.85f)
                        close()
                    }
                    drawPath(path, accentColor)
                }
                "footwear" -> {
                    val path = Path().apply {
                        moveTo(w * 0.25f, h * 0.55f)
                        lineTo(w * 0.45f, h * 0.42f)
                        lineTo(w * 0.62f, h * 0.42f)
                        lineTo(w * 0.82f, h * 0.62f)
                        lineTo(w * 0.82f, h * 0.72f)
                        lineTo(w * 0.2f, h * 0.72f)
                        close()
                    }
                    drawPath(path, accentColor)
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.8f),
                        topLeft = Offset(w * 0.18f, h * 0.70f),
                        size = Size(w * 0.68f, h * 0.1f)
                    )
                }
                "outerwear" -> {
                    val path = Path().apply {
                        moveTo(w * 0.3f, h * 0.15f)
                        lineTo(w * 0.7f, h * 0.15f)
                        lineTo(w * 0.88f, h * 0.42f)
                        lineTo(w * 0.78f, h * 0.5f)
                        lineTo(w * 0.72f, h * 0.45f)
                        lineTo(w * 0.72f, h * 0.88f)
                        lineTo(w * 0.28f, h * 0.88f)
                        lineTo(w * 0.28f, h * 0.45f)
                        lineTo(w * 0.22f, h * 0.5f)
                        lineTo(w * 0.12f, h * 0.42f)
                        close()
                    }
                    drawPath(path, accentColor)
                    drawLine(
                        color = StylloPrimary.copy(alpha = 0.5f),
                        start = Offset(w * 0.5f, h * 0.18f),
                        end = Offset(w * 0.5f, h * 0.88f),
                        strokeWidth = 2.5f
                    )
                }
                else -> {
                    // Accessories (e.g. Watch, Cap, Cuff)
                    drawCircle(
                        color = accentColor,
                        center = Offset(w * 0.5f, h * 0.5f),
                        radius = w * 0.28f,
                        style = Stroke(width = 6f)
                    )
                    drawCircle(
                        color = StylloPrimary,
                        center = Offset(w * 0.5f, h * 0.5f),
                        radius = w * 0.12f
                    )
                }
            }
        }
    }
}
