package io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

interface Colors {
    // MaterialTheme colors
    val primary: Color
    val primaryVariant: Color
    val secondary: Color
    val secondaryVariant: Color
    val background: Color
    val surface: Color
    val error: Color
    val onPrimary: Color
    val onSecondary: Color
    val onBackground: Color
    val onSurface: Color
    val onError: Color

    val designBlue: Color
    val designPurple: Color
}

object LightColors : Colors {
    override val primary = blueLight
    override val primaryVariant: Color = blueDark
    override val secondary: Color = blueLight
    override val secondaryVariant: Color = blueLight
    override val background: Color = BackgroundLight
    override val surface: Color = blueLight
    override val error: Color = errorColorLight
    override val onPrimary: Color = Color.White
    override val onSecondary: Color = Color.White
    override val onBackground: Color = Color.White
    override val onSurface: Color = Color.White
    override val onError: Color = Color.White

    override val designBlue: Color = primary
    override val designPurple: Color = purpleLight
}

object DarkColors : Colors {
    override val primary = blueLight
    override val primaryVariant: Color = blueDark
    override val secondary: Color = blueLight
    override val secondaryVariant: Color = blueLight
    override val background: Color = BackgroundDark
    override val surface: Color = blueLight
    override val error: Color = errorColorDark
    override val onPrimary: Color = Color.White
    override val onSecondary: Color = Color.White
    override val onBackground: Color = Color.White
    override val onSurface: Color = Color.White
    override val onError: Color = Color.Black

    override val designBlue: Color = blueLight
    override val designPurple: Color = purpleLight
}

internal val MaterialLightColors = lightColors(
    primary = LightColors.primary,
    primaryVariant = LightColors.primaryVariant,
    secondary = LightColors.secondary,
    secondaryVariant = LightColors.secondaryVariant,
    background = LightColors.background,
    surface = LightColors.surface,
    error = LightColors.error,
    onPrimary = LightColors.onPrimary,
    onSecondary = LightColors.onSecondary,
    onBackground = LightColors.onBackground,
    onSurface = LightColors.onSurface,
    onError = LightColors.onError
)

internal val MaterialDarkColors = darkColors(
    primary = DarkColors.primary,
    primaryVariant = DarkColors.primaryVariant,
    secondary = DarkColors.secondary,
    secondaryVariant = DarkColors.secondaryVariant,
    background = DarkColors.background,
    surface = DarkColors.surface,
    error = DarkColors.error,
    onPrimary = DarkColors.onPrimary,
    onSecondary = DarkColors.onSecondary,
    onBackground = DarkColors.onBackground,
    onSurface = DarkColors.onSurface,
    onError = DarkColors.onError
)
