package io.github.publicvaluetech.pubandroidweathersample.domain.entity

data class LocationEntity(
    val postcode: String?,
    val city: String,
    val label: String,
    val lat: String,
    val lon: String,
) {
    companion object {
        val DEFAULT = LocationEntity(
            postcode = "80335",
            city = "München",
            label = "80335 München",
            lat = "48.144989",
            lon = "11.554315",
        )
    }
}
