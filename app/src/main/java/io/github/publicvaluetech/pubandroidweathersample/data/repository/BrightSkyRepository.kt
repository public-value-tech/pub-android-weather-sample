package io.github.publicvaluetech.pubandroidweathersample.data.repository

import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeatherResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherForecastResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherRadarCompressedResponse
import kotlinx.coroutines.flow.Flow

interface BrightSkyRepository {
    suspend fun fetchWeatherForecastViaLatLon(
        lat: String,
        lon: String,
        date: String,
        lastDate: String,
    ): Flow<WeatherForecastResponse>

    suspend fun fetchCurrentWeatherViaLatLon(lat: String, lon: String): Flow<CurrentWeatherResponse>

    suspend fun fetchWeatherRadarCompressedViaBBox(bBox: Array<Int>? = null): Flow<WeatherRadarCompressedResponse>
}
