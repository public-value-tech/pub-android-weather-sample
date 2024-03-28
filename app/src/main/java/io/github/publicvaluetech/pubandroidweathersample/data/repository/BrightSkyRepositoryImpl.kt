package io.github.publicvaluetech.pubandroidweathersample.data.repository

import io.github.publicvaluetech.pubandroidweathersample.data.remote.api.WeatherApi
import kotlinx.coroutines.flow.flow

internal class BrightSkyRepositoryImpl(
    private val remote: WeatherApi,
) : BrightSkyRepository {
    override suspend fun fetchWeatherForecastViaLatLon(
        lat: String,
        lon: String,
        date: String,
        lastDate: String,
    ) = flow {
        emit(remote.getWeatherForecastByLatLon(lat, lon, date, lastDate))
    }

    override suspend fun fetchCurrentWeatherViaLatLon(lat: String, lon: String) = flow {
        emit(remote.getCurrentWeatherByLatLon(lat, lon))
    }

    override suspend fun fetchWeatherRadarCompressedViaBBox(bBox: Array<Int>?) = flow {
        emit(remote.getWeatherRadarCompressedByBBox(bBox))
    }
}
