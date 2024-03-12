package io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.github.publicvaluetech.pubandroidweathersample.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeatherFormatter {
    companion object {
        private val weatherIcons = mapOf(
            "fog" to R.drawable.ic_w_fog,
            "rain" to R.drawable.ic_w_extreme_rain,
            "sleet" to R.drawable.ic_w_extreme_sleet,
            "snow" to R.drawable.ic_w_snow,
            "hail" to R.drawable.ic_w_extreme_hail,
            "thunderstorm" to R.drawable.ic_w_thunderstorms_extreme_rain,
            "clear-day" to R.drawable.ic_w_clear_day,
            "clear-night" to R.drawable.ic_w_clear_night,
            "partly-cloudy-day" to R.drawable.ic_w_partly_cloudy_day,
            "partly-cloudy-night" to R.drawable.ic_w_partly_cloudy_night,
            "overcast" to R.drawable.ic_w_overcast,
            "storm" to R.drawable.ic_w_umbrella_wind_alt,
        )

        private val lottieWeatherIcons = mapOf(
            "fog" to R.raw.lottie_w_fog,
            "rain" to R.raw.lottie_w_extreme_rain,
            "sleet" to R.raw.lottie_w_extreme_sleet,
            "snow" to R.raw.lottie_w_snow,
            "hail" to R.raw.lottie_w_extreme_hail,
            "thunderstorm" to R.raw.lottie_w_thunderstorms_extreme_rain,
            "clear-day" to R.raw.lottie_w_clear_day,
            "clear-night" to R.raw.lottie_w_clear_night,
            "partly-cloudy-day" to R.raw.lottie_w_partly_cloudy_day,
            "partly-cloudy-night" to R.raw.lottie_w_partly_cloudy_night,
            "overcast" to R.raw.lottie_w_overcast,
            "storm" to R.raw.lottie_w_umbrella_wind_alt,
        )

        fun getLottieIcon(iconName: String?, conditionName: String?): Int {
            val mappedIconName = mapIconName(conditionName, iconName)
            return lottieWeatherIcons[mappedIconName] ?: R.raw.lottie_w_overcast
        }

        @DrawableRes
        fun getWeatherIcon(iconName: String?, conditionName: String?): Int {
            val mappedIconName = mapIconName(conditionName, iconName)
            return weatherIcons[mappedIconName] ?: R.raw.lottie_w_overcast
        }

        /**
         * Sometimes the iconName and the condition are different. E.g. if we show the string "rain" as condition we should also show the icon for rain.
         * So we go first with the condition and then with the iconName.
         */
        private fun mapIconName(conditionName: String?, iconName: String?) = when (conditionName) {
            "fog" -> "fog"
            "rain" -> "rain"
            "sleet" -> "sleet"
            "snow" -> "snow"
            "hail" -> "hail"
            "thunderstorm" -> "thunderstorm"
            else -> when (iconName) {
                "clear-day" -> "clear-day"
                "clear-night" -> "clear-night"
                "partly-cloudy-day" -> "partly-cloudy-day"
                "partly-cloudy-night" -> "partly-cloudy-night"
                "cloudy" -> "overcast"
                "wind" -> "storm"
                else -> "overcast"
            }
        }

        @StringRes
        fun mapConditionName(conditionName: String?, iconName: String?) = when (conditionName) {
            "dry" -> R.string.condition_dry
            "fog" -> R.string.condition_fog
            "rain" -> R.string.condition_rain
            "sleet" -> R.string.condition_sleet
            "snow" -> R.string.condition_snow
            "hail" -> R.string.condition_hail
            "thunderstorm" -> R.string.condition_thunderstorm
            else -> when (iconName) {
                "clear-day", "clear-night" -> R.string.condition_clear
                "partly-cloudy-day", "partly-cloudy-night" -> R.string.condition_partly_cloudy
                "cloudy" -> R.string.condition_cloudy
                "wind" -> R.string.condition_wind
                else -> R.string.condition_clear
            }
        }

        // Formats ISO 8601-formatted timestamps(2023-08-07T12:30:00+00:00) to eg. "Today - Saturday, 22.04.23"
        fun getDateStringVersion1(context: Context, timestamp: String): String =
            iso8601StringToDate(timestamp).let {
                val locale = context.resources.configuration.locales[0]
                val dateText = formatDateByString(it, WEEKDAY_FULL_DATE_REGEX, locale)
                when {
                    it.isToday() -> context.getString(R.string.today_formatter, dateText)
                    it.isTomorrow() -> context.getString(R.string.tomorrow_formatter, dateText)
                    else -> dateText
                }
            }

        // Formats ISO 8601-formatted timestamps(2023-08-07T12:30:00+00:00) to eg. "Tomorrow" / "Mo, 24.04"
        fun getDateStringVersion2(context: Context, timestamp: String): String = iso8601StringToDate(timestamp).let {
            return if (it.isTomorrow()) {
                context.getString(R.string.tomorrow)
            } else {
                val locale = context.resources.configuration.locales[0]
                Regex("^(\\w\\w)\\.").replaceFirst(formatDateByString(it, WEEKDAY_DATE_REGEX, locale), "$1,")
            }
        }

        // Formats ISO 8601-formatted timestamps(2023-08-07T12:30:00+00:00) to eg. 10:00"
        fun getDateStringVersion3(context: Context, timestamp: String): String = iso8601StringToDate(timestamp).let {
            val locale = context.resources.configuration.locales[0]
            return Regex("^(\\w\\w)\\.").replaceFirst(formatDateByString(it, HOUR_DATE_REGEX, locale), "$1,")
        }

        fun Date.toIso8601String() = SimpleDateFormat(FULL_DATE_REGEX, Locale.GERMANY).format(this)

        fun addDaysToDate(isoDate: String, days: Int): Date = Calendar.getInstance()
            .apply {
                timeInMillis = iso8601StringToDate(isoDate).time + 1000 * 60 * 60 * 24 * days
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 23)
            }.time

        /**
         * Checks if two ISO 8601-formatted timestamps(2023-08-07T12:30:00+00:00) have the same day of a year.
         */
        fun isSameDay(firstDate: String, secondDate: String) = firstDate.substring(0, 10) == secondDate.substring(0, 10)

        fun iso8601StringToDate(isoDate: String): Date = SimpleDateFormat(FULL_DATE_REGEX, Locale.GERMANY).parse(isoDate)

        private fun Date.isToday(): Boolean {
            val calToCheck = Calendar.getInstance()
                .also {
                    it.timeInMillis = this.time
                }
            val now = Calendar.getInstance()
            return isMatchingDay(now, calToCheck)
        }

        private fun Date.isTomorrow(): Boolean {
            val calToCheck = Calendar.getInstance()
                .also {
                    it.timeInMillis = this.time
                }
            val calNextDay = Calendar.getInstance()
            calNextDay.add(Calendar.DATE, 1)
            return isMatchingDay(calNextDay, calToCheck)
        }

        private fun isMatchingDay(firstCal: Calendar, secondCal: Calendar) = isMatchingYear(
            firstCal,
            secondCal
        ) && firstCal.get(Calendar.DAY_OF_YEAR) == secondCal.get(Calendar.DAY_OF_YEAR)

        private fun isMatchingYear(firstCal: Calendar, secondCal: Calendar) =
            firstCal.get(Calendar.YEAR) == secondCal.get(Calendar.YEAR)

        private fun formatDateByString(date: Date, format: String, locale: Locale): String {
            return SimpleDateFormat(format, locale).format(date)
        }

        private const val FULL_DATE_REGEX = "yyyy-MM-dd'T'HH:mm:ss"
        private const val HOUR_DATE_REGEX = "HH:mm"
        private const val WEEKDAY_DATE_REGEX = "EE dd.MM."
        private const val WEEKDAY_FULL_DATE_REGEX = "EEEE, dd.MM.yy - HH:mm"
    }
}
