package io.github.publicvaluetech.pubandroidweathersample.domain

import io.github.publicvaluetech.pubandroidweathersample.data.repository.BrightSkyRepository
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.WeatherRadarEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.toWeatherRadarEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchWeatherRadarUsecase @Inject constructor(
    private val brightSkyRepository: BrightSkyRepository,
) {
    private val resultFlow = MutableSharedFlow<WeatherResult<WeatherRadarEntity>>(replay = 1)
    val results = flow {
        resultFlow.collect {
            emit(it)
        }
    }.onStart {
        emit(
            WeatherResult.loading
        )
    }
    
    suspend operator fun invoke(bBox: Array<Int>? = null) {
        brightSkyRepository.fetchWeatherRadarCompressedViaBBox(bBox).map { weatherRadarResponse ->
            val result = WeatherResult.data(
                weatherRadarResponse.toWeatherRadarEntity()
            )
            resultFlow.emit(
                result
            )
        }.catch {
            val result = WeatherResult.error(it)
            resultFlow.emit(
                result
            )
        }.collect()
    }
}
