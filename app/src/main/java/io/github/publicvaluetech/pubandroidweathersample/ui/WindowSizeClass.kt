package io.github.publicvaluetech.pubandroidweathersample.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
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

        @Composable
        fun current(): WindowSizeClass {
            val screenWidth = LocalConfiguration.current.screenWidthDp
            return when {
                screenWidth < Theme.dimensions.size.compactBreakPoint.value -> Compact
                screenWidth < Theme.dimensions.size.mediumBreakPoint.value -> Medium
                else -> Expanded
            }
        }

        @Composable
        fun isCurrent(expected: WindowSizeClass): Boolean = current() == expected
    }
}
