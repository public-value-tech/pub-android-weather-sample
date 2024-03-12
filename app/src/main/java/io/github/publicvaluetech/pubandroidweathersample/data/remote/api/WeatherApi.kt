package io.github.publicvaluetech.pubandroidweathersample.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.publicvaluetech.pubandroidweathersample.BuildConfig
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeatherResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherForecastResponse
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
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
