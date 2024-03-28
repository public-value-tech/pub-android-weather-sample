package io.github.publicvaluetech.pubandroidweathersample

import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeatherResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherForecastResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherRadarCompressedResponse
import io.github.publicvaluetech.pubandroidweathersample.data.repository.BrightSkyRepository
import kotlinx.coroutines.flow.Flow
import java.io.InputStreamReader

object TestUtils {
    internal fun loadJsonFile(path: String): String {
        val resourceAsStream = javaClass.classLoader?.getResourceAsStream(path)
        val reader = InputStreamReader(resourceAsStream)
        return reader.use { it.readText() }
    }

    internal fun mockRepository(
        flowReturnFetchWeatherViaLatLon: Flow<CurrentWeatherResponse>,
        flowReturnWeatherForecastViaLatLon: Flow<WeatherForecastResponse>,
        flowReturnWeatherRadarCompressedViaBBox: Flow<WeatherRadarCompressedResponse>,
    ) = object : BrightSkyRepository {
        override suspend fun fetchWeatherForecastViaLatLon(
            lat: String,
            lon: String,
            date: String,
            lastDate: String,
        ): Flow<WeatherForecastResponse> = flowReturnWeatherForecastViaLatLon

        override suspend fun fetchCurrentWeatherViaLatLon(lat: String, lon: String): Flow<CurrentWeatherResponse> =
            flowReturnFetchWeatherViaLatLon

        override suspend fun fetchWeatherRadarCompressedViaBBox(bBox: Array<Int>): Flow<WeatherRadarCompressedResponse> =
            flowReturnWeatherRadarCompressedViaBBox
    }
}
