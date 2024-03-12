package io.github.publicvaluetech.pubandroidweathersample.domain

import io.github.publicvaluetech.pubandroidweathersample.data.repository.BrightSkyRepository
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.WeatherEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.toWeatherEntity
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.WeatherFormatter
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.WeatherFormatter.Companion.toIso8601String
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchWeatherUsecase @Inject constructor(
    private val brightSkyRepository: BrightSkyRepository,
) {
    private val resultFlow = MutableSharedFlow<WeatherResult<WeatherEntity>>(replay = 1)
    val results = flow {
        resultFlow.collect {
            emit(it)
        }
    }.onStart {
        emit(
            WeatherResult.loading
        )
    }

    suspend operator fun invoke(
        lat: String,
        lon: String,
    ) {
        resultFlow.emit(
            WeatherResult.loading
        )
        brightSkyRepository.fetchCurrentWeatherViaLatLon(lat, lon)
            .map { currentWeatherResponse ->
                val currentDate = currentWeatherResponse.weather.timestamp
                val currentDatePlus14Days = WeatherFormatter.addDaysToDate(currentDate, 10).toIso8601String()
                brightSkyRepository.fetchWeatherForecastViaLatLon(lat, lon, currentDate, currentDatePlus14Days)
                    .map { forecastResponse ->
                        val result = WeatherResult.data(
                            currentWeatherResponse.toWeatherEntity(
                                forecastResponse
                            )
                        )
                        resultFlow.emit(
                            result
                        )
                    }.collect()
            }.catch {
                val result = WeatherResult.error(it)
                resultFlow.emit(
                    result
                )
            }.collect()
    }
}
