package io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun PubTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (isDarkTheme) MaterialDarkColors else MaterialLightColors
    ) {
        CompositionLocalProvider(
            LocalColors provides if (isDarkTheme) DarkColors else LightColors,
            LocalTypography provides Type
        ) {
            content()
        }
    }
}

object Theme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Type
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}

internal val LocalColors: ProvidableCompositionLocal<Colors> =
    staticCompositionLocalOf { LightColors }

internal val LocalTypography: ProvidableCompositionLocal<Type> =
    staticCompositionLocalOf { Type }

internal val LocalDimensions: ProvidableCompositionLocal<Dimensions> =
    staticCompositionLocalOf { Dimensions() }
