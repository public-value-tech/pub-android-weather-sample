package io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.publicvaluetech.pubandroidweathersample.MainViewModel
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.viewmodel.PubWeatherViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    weatherViewModel: PubWeatherViewModel,
) {
    val scaffoldState = rememberScaffoldState()
    Box(
        Modifier.background(Theme.colors.primaryVariant)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            content = { paddingValue ->
                WeatherScreen(
                    modifier = Modifier,
                    mainViewModel = mainViewModel,
                    weatherViewModel = weatherViewModel,
                    scaffoldState = scaffoldState
                )
            }
        )
    }
}
