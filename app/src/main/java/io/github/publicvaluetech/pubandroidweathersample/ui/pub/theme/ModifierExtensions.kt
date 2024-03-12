package io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import io.github.publicvaluetech.pubandroidweathersample.ui.WindowSizeClass

fun Modifier.defaultContentWidth() =
    composed {
        if (WindowSizeClass.isCurrent(WindowSizeClass.Expanded))
            widthIn(max = Theme.dimensions.size.contentMaxWidth).fillMaxWidth()
        else fillMaxWidth(Theme.dimensions.size.contentWidth)
    }

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
