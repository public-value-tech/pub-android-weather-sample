package io.github.publicvaluetech.pubandroidweathersample.data.location

interface OnLocationRequestListener {
    fun onLatLonResult(lat: Double, lon: Double)
}
