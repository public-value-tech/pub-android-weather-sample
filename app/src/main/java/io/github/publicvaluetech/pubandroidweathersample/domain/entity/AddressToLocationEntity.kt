package io.github.publicvaluetech.pubandroidweathersample.domain.entity

import android.location.Address

fun Address.toLocationEntity() = try {
    val city = subLocality ?: locality ?: throw IllegalStateException()
    val adminArea = if (adminArea != city) ", $adminArea" else ""
    LocationEntity(
        postcode = postalCode,
        city = city,
        label = "${postalCode?.let { "$postalCode " } ?: ""}$city$adminArea",
        lat = latitude.toString(),
        lon = longitude.toString(),
    )
} catch (e: IllegalStateException) {
    null
}
