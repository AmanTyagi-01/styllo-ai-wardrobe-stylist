package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val StylloDarkColorScheme = darkColorScheme(
    primary = StylloPrimary,
    onPrimary = StylloOnPrimary,
    primaryContainer = StylloPrimaryContainer,
    onPrimaryContainer = StylloOnPrimaryContainer,
    secondary = StylloSecondary,
    onSecondary = StylloOnSecondary,
    secondaryContainer = StylloSecondaryContainer,
    onSecondaryContainer = StylloOnSecondaryContainer,
    tertiary = StylloTertiary,
    onTertiary = StylloOnTertiary,
    tertiaryContainer = StylloTertiaryContainer,
    onTertiaryContainer = StylloOnTertiaryContainer,
    background = StylloBackground,
    onBackground = StylloOnSurface,
    surface = StylloSurface,
    onSurface = StylloOnSurface,
    surfaceVariant = StylloSurfaceHighest,
    onSurfaceVariant = StylloOnSurfaceVariant,
    outline = StylloOutline,
    outlineVariant = StylloOutlineVariant,
    error = StylloError,
    onError = StylloOnError,
    errorContainer = StylloErrorContainer,
    onErrorContainer = StylloOnErrorContainer,
    surfaceContainerLowest = StylloSurfaceLowest,
    surfaceContainerLow = StylloSurfaceLow,
    surfaceContainer = StylloSurfaceContainer,
    surfaceContainerHigh = StylloSurfaceHigh,
    surfaceContainerHighest = StylloSurfaceHighest,
)

@Composable
fun StylloTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = StylloSurfaceLowest.toArgb()
            window.navigationBarColor = StylloSurfaceLowest.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = StylloDarkColorScheme,
        typography = Typography,
        content = content
    )
}
