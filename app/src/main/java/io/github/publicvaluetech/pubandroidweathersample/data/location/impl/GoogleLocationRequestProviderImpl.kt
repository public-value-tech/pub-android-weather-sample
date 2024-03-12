package io.github.publicvaluetech.pubandroidweathersample.data.location.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.data.location.LocationRequestProvider
import io.github.publicvaluetech.pubandroidweathersample.data.location.OnLocationRequestListener

@SuppressLint("MissingPermission")
class GoogleLocationRequestProviderImpl(
    context: Context,
    private val locationRequestListener: OnLocationRequestListener,
    private val errorCallback: ((Int) -> Unit)?,
) : LocationRequestProvider {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var cts: CancellationTokenSource? = null

    private val locationListener: OnCompleteListener<Location> =
        OnCompleteListener { task: Task<Location> ->
            if (task.isSuccessful && task.result != null) {
                val location = task.result
                locationRequestListener.onLatLonResult(location.latitude, location.longitude)
            } else {
                errorCallback?.invoke(R.string.location_error_msg)
            }
            stop()
        }

    override fun start() {
        stop()
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val location = task.result
                locationRequestListener.onLatLonResult(location.latitude, location.longitude)
            } else {
                cts = CancellationTokenSource()
                cts?.let {
                    val locationTask =
                        fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            it.token
                        )
                    locationTask.addOnCompleteListener(locationListener)
                }
            }
        }
    }

    override fun stop() {
        cts?.cancel()
        cts = null
    }
}
