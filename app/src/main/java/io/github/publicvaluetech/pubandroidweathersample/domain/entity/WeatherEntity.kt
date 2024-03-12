package io.github.publicvaluetech.pubandroidweathersample.domain.entity

data class WeatherEntity(
    val currentWeather: CurrentWeatherEntity,
    val forecast: ForecastEntity,
)

data class CurrentWeatherEntity(
    val sources: List<SourceEntity>,
    val weather: CurrentWeatherEntityItem,
)

data class CurrentWeatherEntityItem(
    val cloudCover: Int? = null,
    val condition: String? = null,
    val dewPoint: Double? = null,
    val icon: String? = null,
    val pressureMsl: Double? = null,
    val relativeHumidity: Int?,
    val sourceId: Int,
    val temperature: Double? = null,
    val timestamp: String,
    val visibility: Int?,
    val solar10: Double? = null,
    val solar30: Double? = null,
    val solar60: Double? = null,
    val precipitation10: Double? = null,
    val precipitation30: Double? = null,
    val precipitation60: Double? = null,
    val sunshine10: Double? = null,
    val sunshine30: Double? = null,
    val sunshine60: Double? = null,
    val windDirection10: Int? = null,
    val windDirection30: Int? = null,
    val windDirection60: Int? = null,
    val windGustDirection10: Int? = null,
    val windGustDirection30: Int? = null,
    val windGustDirection60: Int? = null,
    val windGustSpeed10: Double? = null,
    val windGustSpeed30: Double? = null,
    val windGustSpeed60: Double? = null,
    val windSpeed10: Double? = null,
    val windSpeed30: Double? = null,
    val windSpeed60: Double? = null,
)

data class ForecastEntity(
    val forecasts: List<ForecastEntityItem> = listOf(),
    val sources: List<SourceEntity> = listOf(),
)

data class ForecastEntityItem(
    val cloudCover: Int? = null,
    val condition: String? = null,
    val dewPoint: Double? = null,
    val icon: String? = null,
    val precipitation: Double? = null,
    val precipitationProbability: Int? = null,
    val pressureMsl: Double? = null,
    val relativeHumidity: Int? = null,
    val sourceId: Int,
    val sunshine: Double? = null,
    val solar: Double? = null,
    val temperature: Double? = null,
    val timestamp: String,
    val visibility: Int? = null,
    val windDirection: Int? = null,
    val windGustDirection: Int? = null,
    val windGustSpeed: Double? = null,
    val windSpeed: Double? = null,
)

data class SourceEntity(
    val id: Int,
    val distance: Double,
    val firstRecord: String,
    val lastRecord: String,
    val height: Double,
    val lat: Double,
    val lon: Double,
    val observationType: String,
    val dwdStationId: String? = null,
    val stationName: String? = null,
    val wmoStationId: String? = null,
)
