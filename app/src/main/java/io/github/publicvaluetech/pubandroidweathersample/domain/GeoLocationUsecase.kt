package io.github.publicvaluetech.pubandroidweathersample.domain

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.LocationEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.toLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

class GeoLocationUsecase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val resultFlow = MutableSharedFlow<WeatherResult<List<LocationEntity>>>(replay = 1)
    val results: Flow<WeatherResult<List<LocationEntity>>> = flow {
        resultFlow.collect {
            emit(it)
        }
    }.onStart {
        emit(
            WeatherResult.loading
        )
    }

    operator fun invoke(locationName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getCitysForName(locationName)
        } else {
            legacyGetCitysForName(locationName)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getCitysForName(locationName: String) {
        val geoCoder = Geocoder(context, Locale.GERMAN)
        try {
            geoCoder.getFromLocationName(locationName, 5, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    getCityForAddress(addresses)
                }

                override fun onError(errorMessage: String?) {
                    emptyList<Address>()
                }
            })
        } catch (t: Throwable) {
            emptyList<Address>()
        }
    }

    @Suppress("deprecation")
    @Deprecated("LegacyApi for Android 32 and below")
    private fun legacyGetCitysForName(locationName: String) {
        val geoCoder = Geocoder(context, Locale.GERMAN)
        val result = try {
            geoCoder.getFromLocationName(locationName, 5) ?: listOf<Address>()
        } catch (t: Throwable) {
            emptyList<Address>()
        }
        getCityForAddress(result)
    }

    private fun getCityForAddress(addresses: List<Address>) {
        runBlocking {
            resultFlow.emit(
                WeatherResult(
                    data = addresses.filter { it.countryCode == "DE" }.mapNotNull { it.toLocationEntity() },
                    error = null,
                    isLoading = false
                )
            )
        }
    }
}
