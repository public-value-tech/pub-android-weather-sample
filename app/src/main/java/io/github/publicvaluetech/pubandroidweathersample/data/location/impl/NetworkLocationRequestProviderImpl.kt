package io.github.publicvaluetech.pubandroidweathersample.data.location.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.data.location.LocationRequestProvider
import io.github.publicvaluetech.pubandroidweathersample.data.location.OnLocationRequestListener

@SuppressLint("MissingPermission")
class NetworkLocationRequestProviderImpl(
    context: Context,
    private val locationRequestListener: OnLocationRequestListener,
    private val errorCallback: ((Int) -> Unit)?,
) : LocationRequestProvider {

    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    }

    private val locationListener: LocationListener by lazy {
        object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationRequestListener.onLatLonResult(location.latitude, location.longitude)
                stop()
            }

            override fun onProviderDisabled(provider: String) {
                errorCallback?.invoke(R.string.location_error_availability_msg)
            }
        }
    }

    override fun start() {
        stop()
        val location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            locationRequestListener.onLatLonResult(location.latitude, location.longitude)
        } else {
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                60_000, // interval in ms
                100f, // distance in meter
                locationListener
            )
        }
    }

    override fun stop() {
        locationManager?.removeUpdates(locationListener)
    }
}
