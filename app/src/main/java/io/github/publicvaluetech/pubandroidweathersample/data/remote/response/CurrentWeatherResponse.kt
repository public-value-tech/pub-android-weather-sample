package io.github.publicvaluetech.pubandroidweathersample.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    @field:Json(name = "sources")
    val sources: List<Source>,
    @field:Json(name = "weather")
    val weather: CurrentWeather,
)

@Keep
@JsonClass(generateAdapter = true)
data class CurrentWeather(
    @field:Json(name = "cloud_cover")
    val cloudCover: Int?,
    @field:Json(name = "condition")
    val condition: String?,
    @field:Json(name = "dew_point")
    val dewPoint: Double?,
    @field:Json(name = "fallback_source_ids")
    val fallbackSourceIds: FallbackSourceIds?,
    @field:Json(name = "icon")
    val icon: String?,
    @field:Json(name = "precipitation_10")
    val precipitation10: Double?,
    @field:Json(name = "precipitation_30")
    val precipitation30: Double?,
    @field:Json(name = "precipitation_60")
    val precipitation60: Double?,
    @field:Json(name = "pressure_msl")
    val pressureMsl: Double?,
    @field:Json(name = "relative_humidity")
    val relativeHumidity: Int?,
    @field:Json(name = "solar_10")
    val solar10: Double?,
    @field:Json(name = "solar_30")
    val solar30: Double?,
    @field:Json(name = "solar_60")
    val solar60: Double?,
    @field:Json(name = "source_id")
    val sourceId: Int,
    @field:Json(name = "sunshine_10")
    val sunshine10: Double?,
    @field:Json(name = "sunshine_30")
    val sunshine30: Double?,
    @field:Json(name = "sunshine_60")
    val sunshine60: Double?,
    @field:Json(name = "temperature")
    val temperature: Double?,
    @field:Json(name = "timestamp")
    val timestamp: String,
    @field:Json(name = "visibility")
    val visibility: Int?,
    @field:Json(name = "wind_direction_10")
    val windDirection10: Int?,
    @field:Json(name = "wind_direction_30")
    val windDirection30: Int?,
    @field:Json(name = "wind_direction_60")
    val windDirection60: Int?,
    @field:Json(name = "wind_gust_direction_10")
    val windGustDirection10: Int?,
    @field:Json(name = "wind_gust_direction_30")
    val windGustDirection30: Int?,
    @field:Json(name = "wind_gust_direction_60")
    val windGustDirection60: Int?,
    @field:Json(name = "wind_gust_speed_10")
    val windGustSpeed10: Double?,
    @field:Json(name = "wind_gust_speed_30")
    val windGustSpeed30: Double?,
    @field:Json(name = "wind_gust_speed_60")
    val windGustSpeed60: Double?,
    @field:Json(name = "wind_speed_10")
    val windSpeed10: Double?,
    @field:Json(name = "wind_speed_30")
    val windSpeed30: Double?,
    @field:Json(name = "wind_speed_60")
    val windSpeed60: Double?,
)

@Keep
@JsonClass(generateAdapter = true)
data class FallbackSourceIds(
    @field:Json(name = "solar_10")
    val solar10: Int?,
    @field:Json(name = "solar_30")
    val solar30: Int?,
    @field:Json(name = "solar_60")
    val solar60: Int?,
)
