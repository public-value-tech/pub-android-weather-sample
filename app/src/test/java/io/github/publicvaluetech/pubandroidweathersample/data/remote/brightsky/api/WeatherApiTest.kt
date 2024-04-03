package io.github.publicvaluetech.pubandroidweathersample.data.remote.brightsky.api

import io.github.publicvaluetech.pubandroidweathersample.TestUtils
import io.github.publicvaluetech.pubandroidweathersample.data.remote.api.weatherApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException

class WeatherApiTest {
    private val mockWebServer = MockWebServer()
    private val api by lazy {
        weatherApi(
            mockWebServer.url("/"),
            client = {
                OkHttpClient.Builder().build()
            })
    }

    @Test
    fun `Get weather forecast via lat lon should Succeed`() = runBlocking {
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(TestUtils.loadJsonFile("brightsky/weatherforecastjsonresponse.json"))
                .setResponseCode(200)
        )
        val weatherForecastResponse = api.getWeatherForecastByLatLon(
            lat = "48.1371079",
            lon = "11.5753822",
            date = "2020-04-21",
            lastDate = "2020-04-22"
        )
        Assert.assertEquals(
            "München-Stadt",
            weatherForecastResponse.sources.first().stationName
        )
        Assert.assertEquals(
            9.6,
            weatherForecastResponse.weather.first().temperature
        )
        Assert.assertEquals(
            "2020-04-21T00:00:00+00:00",
            weatherForecastResponse.weather.first().timestamp
        )
    }

    @Test
    fun `Get current weather via lat lon should Succeed`() = runBlocking {
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(TestUtils.loadJsonFile("brightsky/currentweatherjsonresponse.json"))
                .setResponseCode(200)
        )
        val weatherForecastResponse = api.getCurrentWeatherByLatLon(
            lat = "48.1371079",
            lon = "11.5753822",
        )
        Assert.assertEquals(
            "Muenchen-Stadt",
            weatherForecastResponse.sources.first().stationName
        )
        Assert.assertEquals(
            24.0,
            weatherForecastResponse.weather.temperature
        )
        Assert.assertEquals(
            "2023-07-28T11:10:00+00:00",
            weatherForecastResponse.weather.timestamp
        )
    }

    @Test
    fun `Get current weather should Fail`(): Unit = runBlocking {
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(TestUtils.loadJsonFile("brightsky/currentweatherjsonresponse.json"))
                .setResponseCode(400)
        )

        Assert.assertThrows(HttpException::class.java) {
            runBlocking {
                api.getCurrentWeatherByLatLon(lat = "48.1371079", lon = "11.5753822")
            }
        }.also {
            Assert.assertEquals(400, it.code())
        }
    }

    @Test
    fun `Get radar data for weather via bbox should Succeed`() = runBlocking {
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(TestUtils.loadJsonFile("brightsky/weatherradarcompressedjsonresponse.json"))
                .setResponseCode(200)
        )
        val weatherRadarCompressedResponse = api.getWeatherRadarCompressedByBBox(
            arrayOf(100, 100, 300, 300)
        )
        Assert.assertEquals(
            "RADOLAN::RV::2024-03-27T08:20:00+00:00",
            weatherRadarCompressedResponse.radar.first().source
        )
    }
}
