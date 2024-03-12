package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen.previewItemWeatherUiModelWeek
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherUiModel
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.noRippleClickable

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherWeek(
    modifier: Modifier = Modifier,
    item: WeatherUiModel.Week,
    showHint: (Int) -> Unit,
    dismissSearchTextField: () -> Unit,
) {
    val listState = rememberLazyListState()
    var activeDay by remember { mutableStateOf(item.days.firstOrNull()) }
    val interactionSource = MutableInteractionSource()
    Card(
        modifier = Modifier
            .noRippleClickable { dismissSearchTextField() }
            .padding(
                top = Theme.dimensions.space.space250,
                bottom = Theme.dimensions.space.space250
            ),
        shape = RoundedCornerShape(Theme.dimensions.size.cardCornerRadius),
        backgroundColor = Theme.colors.background,
        elevation = Theme.dimensions.space.space0,
    ) {
        Column {
            Text(
                modifier = modifier.padding(
                    start = Theme.dimensions.space.space400,
                    top = Theme.dimensions.space.space300,
                    bottom = Theme.dimensions.space.space300
                ),
                style = Theme.typography.medium600,
                text = stringResource(id = R.string.weather_week),
                maxLines = 1,
                color = Theme.colors.onBackground,
                textAlign = TextAlign.Start
            )
            LazyRow(
                state = listState,
                modifier = modifier,
                contentPadding = PaddingValues(
                    top = Theme.dimensions.space.space300,
                    bottom = Theme.dimensions.space.space500,
                    start = Theme.dimensions.space.space400,
                    end = Theme.dimensions.space.space400
                ),
                horizontalArrangement = Arrangement.spacedBy(Theme.dimensions.space.space400)
            ) {
                items(item.days) { item ->
                    WeatherWeekDay(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                activeDay = item
                            },
                        item = item,
                        showIndicator = activeDay == item
                    )
                }
            }
            Column(
                modifier = modifier
                    .background(Theme.colors.chipBackground)
                    .fillMaxWidth()
            ) {
                FlowRow(
                    modifier = Modifier.padding(
                        top = Theme.dimensions.space.space500,
                        bottom = Theme.dimensions.space.space300,
                        start = Theme.dimensions.space.space400,
                        end = Theme.dimensions.space.space400,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Theme.dimensions.space.space300),
                ) {
                    activeDay?.risk?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_umbrella_filled,
                            text = stringResource(
                                id = R.string.precipitation_probability_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_precipitation_risk) }
                        )
                    }
                    activeDay?.rainfall?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_rain_filled,
                            text = stringResource(
                                id = R.string.precipitation_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_precipitation) }
                        )
                    }
                    activeDay?.cloudCover?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_cloud_cover_filled,
                            text = stringResource(
                                id = R.string.cloud_cover_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_cloud_cover) }
                        )
                    }
                    activeDay?.humidity?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_humidity_filled,
                            text = stringResource(
                                id = R.string.humidity_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_humidity) }
                        )
                    }
                    activeDay?.pressure?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_pressure,
                            text = stringResource(
                                id = R.string.pressure_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_pressure) }
                        )
                    }
                    activeDay?.dewPoint?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_dew_point_filled,
                            text = stringResource(
                                id = R.string.dew_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_dew_point) }
                        )
                    }
                    activeDay?.visibility?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_visibility_filled,
                            text = stringResource(
                                id = R.string.visibility_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_visibility) }
                        )
                    }
                    activeDay?.windSpeed?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_wind_flag_filled,
                            text = stringResource(
                                id = R.string.wind_formatter,
                                it,
                                activeDay?.windDir?.let { ", $it°" } ?: ""
                            ),
                            onClick = { showHint(R.string.hint_wind) }
                        )
                    }
                    activeDay?.sunshine?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_sunshine_filled,
                            text = stringResource(
                                id = R.string.sunshine_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_sunshine) }
                        )
                    }
                    activeDay?.solarIrradiation?.let {
                        Tag(
                            backgroundColor = Theme.colors.chipBackgroundAlt,
                            iconId = R.drawable.ic_energy,
                            text = stringResource(
                                id = R.string.solar_irradiation_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_solar) }
                        )
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Theme.dimensions.space.space400),
                    contentPadding = PaddingValues(
                        start = Theme.dimensions.space.space400,
                        end = Theme.dimensions.space.space400
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Theme.dimensions.space.space600)
                )
                {
                    items(activeDay?.hours ?: emptyList()) {
                        WeatherHour(item = it)
                    }
                }
            }

        }
    }
}


//region preview
@Preview
@Preview(name = "WeatherWeekDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherWeekHourPreview() {
    ClassicTheme {
        WeatherWeek(item = previewItemWeatherUiModelWeek, showHint = {}, dismissSearchTextField = {})
    }
}
//endregion preview
