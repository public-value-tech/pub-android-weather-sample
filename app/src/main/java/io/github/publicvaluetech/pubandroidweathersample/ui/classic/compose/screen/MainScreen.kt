package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import io.github.publicvaluetech.pubandroidweathersample.MainViewModel
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.viewmodel.ClassicWeatherViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    weatherViewModel: ClassicWeatherViewModel,
) {
    val scaffoldState = rememberScaffoldState()
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.weather_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
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
