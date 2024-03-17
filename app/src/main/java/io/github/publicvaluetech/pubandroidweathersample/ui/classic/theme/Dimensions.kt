package io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val space: Spaces = Spaces(),
    val size: Sizes = Sizes(),
)

data class Spaces(
    val space0: Dp = 0.dp,
    val space100: Dp = 2.dp,
    val space200: Dp = 4.dp,
    val space250: Dp = 6.dp,
    val space300: Dp = 8.dp,
    val space400: Dp = 12.dp,
    val space500: Dp = 16.dp,
    val space600: Dp = 24.dp,
    val space700: Dp = 32.dp,
)

data class Sizes(
    val contentPaddingInPerc: Float = .04255f, // 16/376
    val contentWidth: Float = 1f - 2 * contentPaddingInPerc,
    val contentMaxWidth: Dp = 678.dp, // matches old xml width

    val size0: Dp = 0.dp,
    val cardCornerRadius: Dp = 10.dp,
    val smallIcon: Dp = 24.dp,
    val mediumIcon: Dp = 40.dp,
    val bigIcon: Dp = 70.dp,
    val iconButton: Dp = 28.dp,
    val weatherIcon: Dp = 120.dp,
    val weatherFocusedIndicator: Dp = 2.dp,
    val weatherUnfocusedIndicator: Dp = 1.dp,
    val weatherTextFieldHeight: Dp = 35.dp,
)
