package io.github.publicvaluetech.pubandroidweathersample.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.publicvaluetech.pubandroidweathersample.BuildConfig
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeatherResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherForecastResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherRadarCompressedResponse
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherForecastByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("date") date: String,
        @Query("last_date") lastDate: String,
    ): WeatherForecastResponse

    @GET("current_weather")
    suspend fun getCurrentWeatherByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
    ): CurrentWeatherResponse

    /**
     * Radar data is recorded on a 1200 km (North-South) x 1100 km (East-West) grid, with each pixel representing 1 km².
     * @param bBox BoundingBox (top, left, bottom, right) in pixels, edges are inclusive. (Defaults to full 1200x1100 grid.)
     * @param format Determines how the precipitation data is encoded into the precipitation_5 field (Default: compressed):
     * - compressed: base64-encoded, zlib-compressed bytestring of 2-byte integers
     * - bytes: base64-encoded bytestring of 2-byte integers
     * - plain: Nested array of integers
     */
    @GET("radar")
    suspend fun getWeatherRadarCompressedByBBox(
        @Query("bbox") bBox: Array<Int>? = null,
        @Query("format") format: String = "compressed",
    ): WeatherRadarCompressedResponse
}

const val apiUrl = BuildConfig.brightsky_endpoint_url

fun weatherApi(
    baseUrl: HttpUrl = apiUrl.toHttpUrl(),
    client: () -> OkHttpClient = { makeOkHttpClient() },
): WeatherApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .client(client())
        .build()
    return retrofit.create(WeatherApi::class.java)
}

private fun makeOkHttpClient(
    logging: () -> Interceptor = { loggingInterceptor() },
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(logging())
        .build()

private fun loggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
