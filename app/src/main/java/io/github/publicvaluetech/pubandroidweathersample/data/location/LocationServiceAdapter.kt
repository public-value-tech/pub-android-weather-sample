package io.github.publicvaluetech.pubandroidweathersample.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.data.location.impl.GoogleLocationRequestProviderImpl
import io.github.publicvaluetech.pubandroidweathersample.data.location.impl.NetworkLocationRequestProviderImpl

@SuppressLint("MissingPermission")
class LocationServiceAdapter(
    context: Context,
    locationRequestListener: OnLocationRequestListener,
    private val errorCallback: ((Int) -> Unit)?,
) {

    private val locationProvider: LocationRequestProvider?

    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    }

    init {
        val isGoogleApiAvailable =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        locationProvider = when {
            isGoogleApiAvailable == ConnectionResult.SUCCESS -> {
                GoogleLocationRequestProviderImpl(context, locationRequestListener, errorCallback)
            }

            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true -> {
                NetworkLocationRequestProviderImpl(context, locationRequestListener, errorCallback)
            }

            else -> {
                null
            }
        }
    }

    fun getCurrentLocation() {
        if (locationProvider != null) {
            locationProvider.start()
        } else {
            errorCallback?.invoke(R.string.location_error_msg)
        }
    }

    fun stopGetCurrentLocation() {
        locationProvider?.stop()
    }
}
