package io.github.publicvaluetech.pubandroidweathersample.ui.pub.model

import io.github.publicvaluetech.pubandroidweathersample.domain.entity.CurrentWeatherEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.ForecastEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.ForecastEntityItem
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.LocationEntity
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.WeatherEntity
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.WeatherFormatter
import kotlin.math.max
import kotlin.math.min

fun WeatherEntity.toItems() =
    this.forecast.getForecastEntityItems().let {
        listOf(
            this.toWeatherItemLocation(),
            this.currentWeather.toWeatherItemNow(it.todaysForecastItems),
            toWeatherItemWeek(it.tenDaysForecastItems)
        )
    }

fun List<LocationEntity>.toLocationListItems() = mapNotNull {
    it.toLocationListItem()
}

fun LocationEntity.toLocationListItem() =
    WeatherSubItem.LocationListItem(
        postcode = postcode,
        city = city,
        label = label,
        lat = lat,
        lon = lon,
    )

fun WeatherSubItem.LocationListItem.toLocationEntity() =
    LocationEntity(
        city = city,
        postcode = postcode,
        label = label,
        lat = lat,
        lon = lon,
    )

private fun WeatherEntity.toWeatherItemLocation() = WeatherUiModel.Location(
    timestamp = currentWeather.weather.timestamp
)

private fun CurrentWeatherEntity.toWeatherItemNow(forecastItems: List<ForecastEntityItem>) = WeatherUiModel.Now(
    icon = this.weather.icon,
    actualTemp = this.weather.temperature.toString(),
    maxTemp = forecastItems.getMaxTempForDate()?.let { forecastMaxTemp ->
        this.weather.temperature?.let { currentTemp ->
            max(forecastMaxTemp, currentTemp).toString()
        }
    },
    minTemp = forecastItems.getMinTempForDate()?.let { forecastMinTemp ->
        this.weather.temperature?.let { currentTemp ->
            min(forecastMinTemp, currentTemp).toString()
        }
    },
    condition = this.weather.condition,
    rainfall = this.weather.precipitation60?.toString() ?: "",
    windSpeed = this.weather.windSpeed60?.toString(),
    windDir = this.weather.windDirection60,
    humidity = this.weather.relativeHumidity?.toString(),
    dewPoint = this.weather.dewPoint?.toString(),
    visibility = this.weather.visibility?.toString(),
    solarIrradiation = this.weather.solar60?.toString(),
    sunshine = this.weather.sunshine60?.toString(),
    pressure = this.weather.pressureMsl?.toString(),
    cloudCover = this.weather.cloudCover?.toString(),
    items = forecastItems.map { it.toWeatherSubItemHour() }
)

private fun toWeatherItemWeek(forecastItems: List<ForecastEntityItem>) = WeatherUiModel.Week(
    days = getWeeklyForecast(forecastItems).map { it }
)

private fun ForecastEntityItem.toWeatherSubItemHour() = WeatherSubItem.Hour(
    timestamp = timestamp,
    icon = icon,
    condition = condition,
    temp = temperature?.toString() ?: ""
)

private fun getWeeklyForecast(items: List<ForecastEntityItem>): List<WeatherSubItem.WeekDay> {
    val listOfWeekDays = mutableListOf<WeatherSubItem.WeekDay>()
    items.forEachIndexed { index, item ->
        if (index == 0) {
            val maxTemp = items.getMaxTempForDate(item.timestamp)
            val minTemp = items.getMinTempForDate(item.timestamp)
            listOfWeekDays.add(
                item.toWeatherSubItemWeekDay(
                    maxTemp,
                    minTemp,
                    getForecastEntityItemsWithSameDate(items, item.timestamp)
                )
            )
        } else if (index > 0 && !WeatherFormatter.isSameDay(item.timestamp, items[index - 1].timestamp)) {
            val maxTemp = items.getMaxTempForDate(item.timestamp)
            val minTemp = items.getMinTempForDate(item.timestamp)
            listOfWeekDays.add(
                item.toWeatherSubItemWeekDay(
                    maxTemp,
                    minTemp,
                    getForecastEntityItemsWithSameDate(items, item.timestamp)
                )
            )
        }
    }
    return listOfWeekDays
}

private fun ForecastEntityItem.toWeatherSubItemWeekDay(
    maxTemp: Double?,
    minTemp: Double?,
    items: List<ForecastEntityItem>
): WeatherSubItem.WeekDay {
    // Get the information's for 8:00 a clock else 0:00 a clock
    val eightAClockItem = if (items.count() > 8) items[8] else this
    return WeatherSubItem.WeekDay(
        icon = eightAClockItem.icon,
        condition = eightAClockItem.condition,
        timestamp = eightAClockItem.timestamp,
        maxTemp = maxTemp?.toString(),
        minTemp = minTemp?.toString(),
        rainfall = eightAClockItem.precipitation?.toString(),
        risk = eightAClockItem.precipitationProbability?.toString(),
        windSpeed = eightAClockItem.windSpeed?.toString(),
        windDir = eightAClockItem.windDirection,
        humidity = eightAClockItem.relativeHumidity?.toString(),
        dewPoint = eightAClockItem.dewPoint?.toString(),
        visibility = eightAClockItem.visibility?.toString(),
        solarIrradiation = eightAClockItem.solar?.toString(),
        sunshine = eightAClockItem.sunshine?.toString(),
        pressure = eightAClockItem.pressureMsl?.toString(),
        cloudCover = eightAClockItem.cloudCover?.toString(),
        hours = items.map {
            WeatherSubItem.Hour(
                timestamp = it.timestamp,
                icon = it.icon,
                condition = it.condition,
                temp = it.temperature.toString()
            )
        }
    )
}


private fun getForecastEntityItemsWithSameDate(items: List<ForecastEntityItem>, timestamp: String) =
    items.mapNotNull { item ->
        if (WeatherFormatter.isSameDay(item.timestamp, timestamp)) {
            item
        } else null
    }

fun List<ForecastEntityItem>.getMaxTempForDate(timestamp: String? = null) =
    getTempOfForecastEntityItemsWithSameDate(this, timestamp).maxOrNull()

fun List<ForecastEntityItem>.getMinTempForDate(timestamp: String? = null) =
    getTempOfForecastEntityItemsWithSameDate(this, timestamp).minOrNull()

private fun getTempOfForecastEntityItemsWithSameDate(items: List<ForecastEntityItem>, timestamp: String?) =
    items.mapNotNull { item ->
        if (timestamp == null) {
            item.temperature
        } else {
            if (WeatherFormatter.isSameDay(item.timestamp, timestamp)) {
                item.temperature
            } else null
        }
    }

private fun ForecastEntity.getForecastEntityItems(): ForecastEntityItems {
    val itemsForToday = mutableListOf<ForecastEntityItem>()
    val itemsFor10DaysForecast = mutableListOf<ForecastEntityItem>()
    val todaysDate = this.forecasts.first().timestamp.substring(0, 10)
    this.forecasts.forEachIndexed { index, item ->
        item.timestamp.substring(0, 10).let {
            when {
                it == todaysDate && index > 1 -> itemsForToday.add(item) // First bucket is the last hour, second is the current hour
                it != todaysDate -> {
                    if (itemsForToday.count() < 8) {
                        itemsForToday.add(item)
                    }
                    itemsFor10DaysForecast.add(item)
                }
            }
        }
    }
    return ForecastEntityItems(
        todaysForecastItems = itemsForToday,
        tenDaysForecastItems = itemsFor10DaysForecast
    )
}

private data class ForecastEntityItems(
    val todaysForecastItems: List<ForecastEntityItem>,
    val tenDaysForecastItems: List<ForecastEntityItem>,
)
