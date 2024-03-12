package io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme

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
    val space550: Dp = 20.dp,
    val space600: Dp = 24.dp,
    val space700: Dp = 32.dp,
    val space800: Dp = 48.dp,
    val space900: Dp = 60.dp,
    val space1000: Dp = 72.dp,
    val space1100: Dp = 96.dp,
    val space1200: Dp = 128.dp,
    val space1300: Dp = 192.dp,
    val space1400: Dp = 256.dp,
    val space1500: Dp = 384.dp,
    val space1600: Dp = 512.dp,
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
    val weatherButtonRadius: Dp = 40.dp,
    val weatherBorderSize: Dp = 2.dp,
    val weatherFocusedIndicator: Dp = 2.dp,
    val weatherUnfocusedIndicator: Dp = 1.dp,
    val weatherTextFieldHeight: Dp = 35.dp,
    // Compact | 600dp | Medium | 840dp | Expanded
    val compactBreakPoint: Dp = 600.dp,
    val mediumBreakPoint: Dp = 840.dp,
)
