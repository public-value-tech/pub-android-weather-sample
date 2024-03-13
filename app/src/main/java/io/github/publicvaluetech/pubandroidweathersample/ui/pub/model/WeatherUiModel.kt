package io.github.publicvaluetech.pubandroidweathersample.ui.pub.model

sealed class WeatherUiModel {
    abstract val type: Int

    data class Location(
        override val type: Int = Location::class.java.hashCode(),
        val timestamp: String,
    ) : WeatherUiModel()

    data class Now(
        override val type: Int = Now::class.java.hashCode(),
        val icon: String?,
        val actualTemp: String?,
        val maxTemp: String?,
        val minTemp: String?,
        val condition: String?,
        val rainfall: String?,
        val windSpeed: String?,
        val windDir: Int?,
        val humidity: String?,
        val dewPoint: String?,
        val visibility: String?,
        val solarIrradiation: String?,
        val sunshine: String?,
        val pressure: String?,
        val cloudCover: String?,
        val items: List<WeatherSubItem.Hour>,
    ) : WeatherUiModel()

    data class Week(
        override val type: Int = Week::class.java.hashCode(),
        val days: List<WeatherSubItem.WeekDay>,
    ) : WeatherUiModel()
}

sealed class WeatherSubItem {
    data class WeekDay(
        val icon: String?,
        val condition: String?,
        val timestamp: String,
        val maxTemp: String?,
        val minTemp: String?,
        val rainfall: String?,
        val risk: String?,
        val windSpeed: String?,
        val windDir: Int?,
        val humidity: String?,
        val dewPoint: String?,
        val visibility: String?,
        val solarIrradiation: String?,
        val sunshine: String?,
        val pressure: String?,
        val cloudCover: String?,
        val hours: List<Hour>,
    ) : WeatherSubItem()

    data class Hour(
        val timestamp: String,
        val icon: String?,
        val condition: String?,
        val temp: String,
    ) : WeatherSubItem()

    data class LocationListItem(
        val postcode: String?,
        val city: String,
        val label: String,
        val lat: String = "48.144989",
        val lon: String = "11.554315",
    ) : WeatherSubItem() {
        companion object {
            val DEFAULT = LocationListItem(
                postcode = "80335",
                city = "München",
                label = "80335 München",
                lat = "48.144989",
                lon = "11.554315",
            )
        }
    }
}
