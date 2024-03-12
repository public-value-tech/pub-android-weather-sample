package io.github.publicvaluetech.pubandroidweathersample.ui.model.brightsky

import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.WeatherFormatter
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.WeatherFormatter.Companion.toIso8601String
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class WeatherFormatterTest {
    @Test
    fun `ISO 8601 dates comparison Should succeed`() {
        val firstIso8601Date = "2023-08-08T08:00:00+02:00"
        val secondIso8601Date = "2023-08-08T023:00:00+02:00"

        assert(WeatherFormatter.isSameDay(firstIso8601Date, secondIso8601Date))
    }

    @Test
    fun `ISO 8601 date string to date Should succeed`() {
        val actualIso8601Date = WeatherFormatter.iso8601StringToDate("2023-08-08T08:00:00+02:00")
        val expectedDate = Date(1691474400000)

        assertEquals(expectedDate.time, actualIso8601Date?.time)
    }

    @Test
    fun `ISO 8601 date plus one day Should succeed`() {
        val actualIso8601Date = WeatherFormatter.addDaysToDate("2023-08-08T08:00:00+02:00", 1)
        val expectedDate = Date(1691616180000)

        assertEquals(expectedDate.time, actualIso8601Date?.time)
    }

    @Test
    fun `Date to ISO 8601 string Should succeed`() {
        val actualIso8601Date = Date(1691474400000).toIso8601String()
        val expectedDate = "2023-08-08T08:00:00"

        assertEquals(expectedDate, actualIso8601Date)
    }
}
