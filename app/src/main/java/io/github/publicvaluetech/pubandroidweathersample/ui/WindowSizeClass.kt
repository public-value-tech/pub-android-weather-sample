package io.github.publicvaluetech.pubandroidweathersample.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.Theme

/**
 * Windows size class depending on the current screen width
 * available values are Compact, Medium and Expanded
 * used break points are Compact | 600dp | Medium | 840dp | Expanded
 * mimics material3-window-size-class
 *
 * see https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#window_size_classes
 * and https://developer.android.com/reference/kotlin/androidx/compose/material3/windowsizeclass/package-summary
 */
enum class WindowSizeClass {
    Compact, Medium, Expanded;

    companion object {
        // Compact | 600dp | Medium | 840dp | Expanded
        private val compactBreakPoint: Dp = 600.dp
        private val mediumBreakPoint: Dp = 840.dp

        @Composable
        fun current(): WindowSizeClass {
            val screenWidth = LocalConfiguration.current.screenWidthDp
            return when {
                screenWidth < compactBreakPoint.value -> Compact
                screenWidth < mediumBreakPoint.value -> Medium
                else -> Expanded
            }
        }

        @Composable
        fun isCurrent(expected: WindowSizeClass): Boolean = current() == expected
    }
}
