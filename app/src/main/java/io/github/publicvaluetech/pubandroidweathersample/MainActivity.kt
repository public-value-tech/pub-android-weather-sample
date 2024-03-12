package io.github.publicvaluetech.pubandroidweathersample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import dagger.hilt.android.AndroidEntryPoint
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.viewmodel.ClassicWeatherViewModel
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.PubTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.viewmodel.PubWeatherViewModel

// An AppCompatActivity is necessary to use AppCompatDelegate for language pickers
// https://developer.android.com/guide/topics/resources/app-languages#androidx-impl
// https://github.com/android/user-interface-samples/blob/main/PerAppLanguages/compose_app/app/src/main/java/com/example/perapplanguages/MainActivity.kt
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel by viewModels()
        val classicWeatherViewModel: ClassicWeatherViewModel by viewModels()
        val pubWeatherViewModel: PubWeatherViewModel by viewModels()
        setContent {
            mainViewModel.setInitialLocale(LocalConfiguration.current.locales[0])
            val currentDesign by mainViewModel.design.collectAsState()
            when (currentDesign) {
                Design.CLASSIC -> ClassicTheme {
                    io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen.MainScreen(
                        mainViewModel = mainViewModel,
                        weatherViewModel = classicWeatherViewModel
                    )
                }
                Design.PUB -> PubTheme {
                    io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.screen.MainScreen(
                        mainViewModel = mainViewModel,
                        weatherViewModel = pubWeatherViewModel
                    )
                }
            }
        }
    }
}
