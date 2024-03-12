package io.github.publicvaluetech.pubandroidweathersample.ui

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun SlideInAnimation(content: @Composable () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !LocalInspectionMode.current) {
        var isAnimationDone by rememberSaveable { mutableStateOf(false) }

        val visible = remember {
            MutableTransitionState(initialState = isAnimationDone).apply {
                // Start the animation immediately.
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = visible,
            enter = fadeIn(
                tween(500),
                initialAlpha = 0.3f
            ) + scaleIn(
                tween(500),
                initialScale = 0.8f
            )
        ) {
            content()
        }.also {
            isAnimationDone = true
        }
    } else {
        content()
    }
}
