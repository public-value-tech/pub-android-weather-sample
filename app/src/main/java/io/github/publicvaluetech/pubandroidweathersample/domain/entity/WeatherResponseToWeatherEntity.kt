package io.github.publicvaluetech.pubandroidweathersample.domain.entity

import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeather
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.CurrentWeatherResponse
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.Source
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.Weather
import io.github.publicvaluetech.pubandroidweathersample.data.remote.response.WeatherForecastResponse

internal fun CurrentWeatherResponse.toWeatherEntity(
    forecast: WeatherForecastResponse,
) = WeatherEntity(
    currentWeather = this.toCurrentWeatherEntity(),
    forecast = forecast.toForecastEntity(),
)

private fun CurrentWeatherResponse.toCurrentWeatherEntity() = CurrentWeatherEntity(
    sources = sources.map { it.toSourceEntity() },
    weather = weather.toCurrentWeatherEntityItem()
)

private fun WeatherForecastResponse.toForecastEntity() = ForecastEntity(
    forecasts = weather.map { it.toForecastEntityItem() },
    sources = sources.map { it.toSourceEntity() }
)

private fun CurrentWeather.toCurrentWeatherEntityItem() = CurrentWeatherEntityItem(
    cloudCover = cloudCover,
    condition = condition,
    dewPoint = dewPoint,
    icon = icon,
    pressureMsl = pressureMsl,
    relativeHumidity = relativeHumidity,
    sourceId = sourceId,
    temperature = temperature,
    timestamp = timestamp,
    visibility = visibility,
    solar10 = solar10,
    solar30 = solar30,
    solar60 = solar60,
    precipitation10 = precipitation10,
    precipitation30 = precipitation30,
    precipitation60 = precipitation60,
    sunshine10 = sunshine10,
    sunshine30 = sunshine30,
    sunshine60 = sunshine60,
    windDirection10 = windDirection10,
    windDirection30 = windDirection30,
    windDirection60 = windDirection60,
    windGustDirection10 = windGustDirection10,
    windGustDirection30 = windGustDirection30,
    windGustDirection60 = windGustDirection60,
    windGustSpeed10 = windGustSpeed10,
    windGustSpeed30 = windGustSpeed30,
    windGustSpeed60 = windGustSpeed60,
    windSpeed10 = windSpeed10,
    windSpeed30 = windSpeed30,
    windSpeed60 = windSpeed60
)

private fun Weather.toForecastEntityItem() = ForecastEntityItem(
    cloudCover = cloudCover,
    condition = condition,
    dewPoint = dewPoint,
    icon = icon,
    precipitation = precipitation,
    precipitationProbability = precipitationProbability,
    pressureMsl = pressureMsl,
    relativeHumidity = relativeHumidity,
    sourceId = sourceId,
    sunshine = sunshine,
    solar = solar,
    temperature = temperature,
    timestamp = timestamp,
    visibility = visibility,
    windDirection = windDirection,
    windGustDirection = windGustDirection,
    windGustSpeed = windGustSpeed,
    windSpeed = windSpeed
)

private fun Source.toSourceEntity() = SourceEntity(
    id = id,
    distance = distance,
    firstRecord = firstRecord,
    lastRecord = lastRecord,
    height = height,
    lat = lat,
    lon = lon,
    observationType = observationType,
    dwdStationId = dwdStationId,
    stationName = stationName,
    wmoStationId = wmoStationId
)
