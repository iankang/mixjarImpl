package com.lunna.mixjarimpl.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Grey500,
    primaryVariant = Grey700,
    secondary = Grey200,
    onBackground = Color.White,
)

private val LightColorPalette = lightColors(
    primary = BlueGrey500,
    primaryVariant = BlueGrey700,
    secondary = BlueGrey200,
    onBackground = Grey700,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,

    onSurface = Color.Black,
    */
)

@Composable
fun MixjarImplTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}