package io.github.publicvaluetech.pubandroidweathersample.data.domain

import io.github.publicvaluetech.pubandroidweathersample.TestUtils
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeatherResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherForecastResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherRadarCompressedResponse
import io.github.publicvaluetech.pubandroidweathersample.domain.FetchWeatherUsecase
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.UnknownHostException

class FetchWeatherUsecaseTest {
    @Test
    fun `fetch Then return Loading`() = runBlocking {
        val hitCurrentWeatherResponse = mockk<CurrentWeatherResponse>()
        val hitWeatherForecastResponse = mockk<WeatherForecastResponse>()
        val hitWeatherRadarCompressedResponse = mockk<WeatherRadarCompressedResponse>()
        val repository = TestUtils.mockRepository(
            flowOf(hitCurrentWeatherResponse),
            flowOf(hitWeatherForecastResponse),
            flowOf(hitWeatherRadarCompressedResponse)
        )
        val usecase = FetchWeatherUsecase(repository)
        usecase.invoke("", "")
        val resultsFromFlow = usecase.results.first()

        assert(resultsFromFlow.isLoading)
    }

    @Test
    fun `fetch with exception Then return Error`() = runBlocking {
        val repository = TestUtils.mockRepository(
            flow { throw UnknownHostException() },
            flow { throw UnknownHostException() },
            flow { throw UnknownHostException() },
        )
        val usecase = FetchWeatherUsecase(repository)
        usecase.invoke("", "")
        val resultsFromFlow = usecase.results.drop(1).first()

        assert(resultsFromFlow.error is UnknownHostException)
    }

    @Test
    fun `fetch with None empty Results When no timestamp Then Loading Then Error`() = runBlocking {
        val hitCurrentWeatherResponse = mockk<CurrentWeatherResponse>()
        val hitWeatherForecastResponse = mockk<WeatherForecastResponse>()
        val hitWeatherRadarCompressedResponse = mockk<WeatherRadarCompressedResponse>()
        val repository = TestUtils.mockRepository(
            flowOf(hitCurrentWeatherResponse),
            flowOf(hitWeatherForecastResponse),
            flowOf(hitWeatherRadarCompressedResponse),
        )
        val usecase = FetchWeatherUsecase(repository)
        usecase.invoke("", "")
        val resultsFromFlow = usecase.results

        assert(resultsFromFlow.first().isLoading)
        assert(resultsFromFlow.drop(1).first().error != null)
    }
}
