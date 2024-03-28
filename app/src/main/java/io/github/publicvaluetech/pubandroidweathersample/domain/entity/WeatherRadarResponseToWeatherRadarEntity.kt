package io.github.publicvaluetech.pubandroidweathersample.domain.entity

import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.Geometry
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.Radar
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherRadarCompressedResponse

internal fun WeatherRadarCompressedResponse.toWeatherRadarEntity() = WeatherRadarEntity(
    geometry = geometry.toGeoBBox(),
    radar = radar.toWeatherRadarData()
)

private fun Geometry.toGeoBBox() = GeoBBox(
    coordinates = coordinates,
    type = type
)

private fun List<Radar>.toWeatherRadarData() = this.map {
    WeatherRadarData(
        timestamp = it.timestamp,
        precipitationBase64 = it.precipitation5,
        source = it.source
    )
}
