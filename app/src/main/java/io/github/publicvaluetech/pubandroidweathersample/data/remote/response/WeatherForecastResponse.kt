package io.github.publicvaluetech.pubandroidweathersample.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class WeatherForecastResponse(
    @field:Json(name = "sources")
    val sources: List<Source>,
    @field:Json(name = "weather")
    val weather: List<Weather>,
)

@Keep
@JsonClass(generateAdapter = true)
data class Source(
    @field:Json(name = "distance")
    val distance: Double,
    @field:Json(name = "dwd_station_id")
    val dwdStationId: String?,
    @field:Json(name = "first_record")
    val firstRecord: String,
    @field:Json(name = "height")
    val height: Double,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "last_record")
    val lastRecord: String,
    @field:Json(name = "lat")
    val lat: Double,
    @field:Json(name = "lon")
    val lon: Double,
    @field:Json(name = "observation_type")
    val observationType: String,
    @field:Json(name = "station_name")
    val stationName: String?,
    @field:Json(name = "wmo_station_id")
    val wmoStationId: String?,
)

@Keep
@JsonClass(generateAdapter = true)
data class Weather(
    @field:Json(name = "cloud_cover")
    val cloudCover: Int?,
    @field:Json(name = "condition")
    val condition: String?,
    @field:Json(name = "dew_point")
    val dewPoint: Double?,
    @field:Json(name = "icon")
    val icon: String?,
    @field:Json(name = "precipitation")
    val precipitation: Double?,
    @field:Json(name = "precipitation_probability")
    val precipitationProbability: Int?,
    @field:Json(name = "pressure_msl")
    val pressureMsl: Double?,
    @field:Json(name = "relative_humidity")
    val relativeHumidity: Int?,
    @field:Json(name = "source_id")
    val sourceId: Int,
    @field:Json(name = "sunshine")
    val sunshine: Double?,
    @field:Json(name = "solar")
    val solar: Double?,
    @field:Json(name = "temperature")
    val temperature: Double?,
    @field:Json(name = "timestamp")
    val timestamp: String,
    @field:Json(name = "visibility")
    val visibility: Int?,
    @field:Json(name = "wind_direction")
    val windDirection: Int?,
    @field:Json(name = "wind_gust_direction")
    val windGustDirection: Int?,
    @field:Json(name = "wind_gust_speed")
    val windGustSpeed: Double?,
    @field:Json(name = "wind_speed")
    val windSpeed: Double?,
)
