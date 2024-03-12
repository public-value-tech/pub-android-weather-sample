package io.github.publicvaluetech.pubandroidweathersample.domain

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.data.location.LocationServiceAdapter
import io.github.publicvaluetech.pubandroidweathersample.data.location.OnLocationRequestListener
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.LocationEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.toLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

class ReverseGeoLocationUsecase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    lateinit var locationErrorCallback: ((Int) -> Unit)
    private val resultFlow = MutableSharedFlow<WeatherResult<LocationEntity>>(replay = 1)
    val results: Flow<WeatherResult<LocationEntity>> = flow {
        resultFlow.collect {
            emit(it)
        }
    }.onStart {
        emit(
            WeatherResult.loading
        )
    }

    operator fun invoke() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
        val isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
        if (isGpsEnabled || isNetworkEnabled) {
            locationServiceAdapter.getCurrentLocation()
        } else {
            locationErrorCallback.invoke(R.string.location_error_availability_msg)
        }
    }

    fun detach() {
        locationServiceAdapter.stopGetCurrentLocation()
    }

    private var requestedCurrentLocation = false
    private val locationRequestListener = object : OnLocationRequestListener {
        @Suppress("deprecation")
        override fun onLatLonResult(lat: Double, lon: Double) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getCityForLatLon(lat, lon)
            } else {
                legacyGetCityForLatLon(lat, lon)
            }
        }
    }
    private val locationServiceAdapter by lazy {
        LocationServiceAdapter(context, locationRequestListener, locationErrorCallback)
    }

    /**
     * Takes lat and lon values to find one address nearby with Geocoder
     * A location search is then started with the postal code when a address item was found
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getCityForLatLon(lat: Double, lon: Double) {
        val geoCoder = Geocoder(context, Locale.GERMAN)
        try {
            geoCoder.getFromLocation(lat, lon, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    getCityForAddress(addresses.firstOrNull())
                }

                override fun onError(errorMessage: String?) {
                    getCityForAddress(null)
                }
            })
        } catch (t: Throwable) {
            getCityForAddress(null)
        }
    }

    /**
     *  Legacy version for <= API 32 (Android 12.1/12L)
     *  Takes lat and lon values to find one address nearby with Geocoder
     *  A location search is then started with the postal code when a address item was found
     */
    @Suppress("deprecation")
    @Deprecated("LegacyApi for Android 32 and below")
    private fun legacyGetCityForLatLon(lat: Double, lon: Double) {
        val geoCoder = Geocoder(context, Locale.GERMAN)
        val result = try {
            geoCoder.getFromLocation(lat, lon, 1) ?: listOf<Address>()
        } catch (t: Throwable) {
            // if any error happens here, we simply fail out of here - with an empty list
            listOf<Address>()
        }
        getCityForAddress(result.firstOrNull())
    }

    private fun getCityForAddress(address: Address?) {
        if (address?.countryCode == "DE") {
            requestedCurrentLocation = true
            runBlocking {
                resultFlow.emit(
                    WeatherResult(
                        data = address.toLocationEntity(),
                        error = null,
                        isLoading = false
                    )
                )
            }
        } else {
            locationErrorCallback.invoke(R.string.location_error_msg)
        }
    }
}
