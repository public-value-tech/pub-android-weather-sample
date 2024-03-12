package io.github.publicvaluetech.pubandroidweathersample.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

data class WeatherResult<out T : Any>(
    val data: T?,
    val error: Throwable?,
    val isLoading: Boolean,
) {
    companion object {
        val initial = WeatherResult(data = null, error = null, isLoading = false)
        val loading = WeatherResult(data = null, error = null, isLoading = true)

        fun error(throwable: Throwable) = WeatherResult(data = null, error = throwable, isLoading = false)
        fun <T : Any> data(data: T) = WeatherResult(data = data, error = null, isLoading = false)
    }
}

fun <T : Any> Flow<WeatherResult<T>>.successData(): Flow<T> = this
    .mapNotNull { it.data }
