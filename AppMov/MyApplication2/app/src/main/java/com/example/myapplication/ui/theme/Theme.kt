package com.example.myapplication.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.example.myapplication.R


val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_medium),
    Font(R.font.poppins_semibold, FontWeight.Bold)
)



private val lightScheme = lightColorScheme(
    primary = blue, //app bars
    onPrimary = white,
    primaryContainer = white, //cards and fields
    onPrimaryContainer = blue,
    secondary = grey, //main card
    onSecondary = blue,
    secondaryContainer = yellow, //stat card
    onSecondaryContainer = white,
    tertiary = blue, //buttons
    onTertiary = white,
    tertiaryContainer = yellow, //floating button
    onTertiaryContainer = white,
    error = red,
    surface = white,
    onSurface = blue,
    outline = yellow,
    outlineVariant = blue, //titles
    inverseSurface = disabledButtonColor,
    inverseOnSurface = white,
    inversePrimary = grey,
)

private val darkScheme = darkColorScheme(
    primary = blue,
    onPrimary = white,
    primaryContainer = blue,
    onPrimaryContainer = white,
    secondary = darkBlue,
    onSecondary = white,
    secondaryContainer = blue,
    onSecondaryContainer = white,
    tertiary = yellow,
    onTertiary = white,
    tertiaryContainer = yellow,
    onTertiaryContainer = white,
    error = red,
    surface = blue,
    onSurface = white,
    outline = yellow,
    outlineVariant = yellow,
    inverseSurface = disabledButtonColorDark,
    inverseOnSurface = white,
    inversePrimary = grey,
)


@Composable
fun GourmetManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}