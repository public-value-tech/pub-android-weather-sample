package io.github.publicvaluetech.pubandroidweathersample.domain.entity

data class WeatherRadarEntity(
    val geometry: GeoBBox,
    val radar: List<WeatherRadarData>,
)

data class GeoBBox(
    val coordinates: List<List<Double>>,
    val type: String,
)

data class WeatherRadarData(
    val timestamp: String,
    val precipitationBase64: String,
    val source: String,
)
