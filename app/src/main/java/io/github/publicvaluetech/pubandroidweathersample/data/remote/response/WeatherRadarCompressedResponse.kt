package io.github.publicvaluetech.pubandroidweathersample.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class WeatherRadarCompressedResponse(
    @field:Json(name = "bbox")
    val bbox: List<Int>?,
    @field:Json(name = "geometry")
    val geometry: Geometry,
    @field:Json(name = "latlon_position")
    val latLonPosition: LatLonPosition?,
    @field:Json(name = "radar")
    val radar: List<Radar>,
)

@Keep
@JsonClass(generateAdapter = true)
data class Geometry(
    @field:Json(name = "coordinates")
    val coordinates: List<List<Double>>,
    @field:Json(name = "type")
    val type: String,
)

@Keep
@JsonClass(generateAdapter = true)
data class LatLonPosition(
    @field:Json(name = "x")
    val x: Double?,
    @field:Json(name = "y")
    val y: Double?,
)

@Keep
@JsonClass(generateAdapter = true)
data class Radar(
    @field:Json(name = "precipitation_5")
    val precipitation5: String,
    @field:Json(name = "source")
    val source: String,
    @field:Json(name = "timestamp")
    val timestamp: String,
)
