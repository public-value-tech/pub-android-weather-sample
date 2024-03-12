package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.component

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen.previewItemWeatherUiModelNow
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherUiModel
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.WeatherFormatter
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.noRippleClickable

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun WeatherNow(
    modifier: Modifier = Modifier,
    item: WeatherUiModel.Now,
    showHint: (Int) -> Unit,
    dismissSearchTextField: () -> Unit,
) {
    Card(
        modifier = modifier
            .noRippleClickable { dismissSearchTextField() }
            .padding(
                top = Theme.dimensions.space.space250,
                bottom = Theme.dimensions.space.space250
            ),
        shape = RoundedCornerShape(Theme.dimensions.size.cardCornerRadius),
        backgroundColor = Theme.colors.background,
        elevation = Theme.dimensions.space.space0,
    ) {
        Text(
            modifier = modifier.padding(
                start = Theme.dimensions.space.space400,
                top = Theme.dimensions.space.space300
            ),
            style = Theme.typography.medium600,
            text = stringResource(id = R.string.weather_today),
            maxLines = 1,
            color = Theme.colors.onBackground,
            textAlign = TextAlign.Start
        )
        Column(
            modifier = Modifier.padding(top = Theme.dimensions.space.space700)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = Theme.dimensions.space.space400)
                            .weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieIcon(
                            WeatherFormatter.getLottieIcon(item.icon, item.condition), Theme.dimensions.size.weatherIcon
                        )
                        Text(
                            modifier = Modifier,
                            style = Theme.typography.bold600,
                            text = stringResource(id = WeatherFormatter.mapConditionName(item.condition, item.icon)),
                            maxLines = 1,
                            color = Theme.colors.onBackground,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(end = Theme.dimensions.space.space400)
                            .weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            item.actualTemp?.let {
                                Text(
                                    style = Theme.typography.normal1600,
                                    text = stringResource(
                                        id = R.string.temp_celsius_formatter,
                                        it
                                    ),
                                    maxLines = 1,
                                    color = Theme.colors.onBackground,
                                    textAlign = TextAlign.End
                                )
                            }
                            Row(
                            ) {
                                item.maxTemp?.let {
                                    Text(
                                        style = Theme.typography.normal800,
                                        text = stringResource(
                                            id = R.string.temp_formatter,
                                            it
                                        ),
                                        maxLines = 1,
                                        color = Theme.colors.onBackground,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        modifier = Modifier
                                            .alpha(0.5F)
                                            .padding(
                                                top = Theme.dimensions.space.space400,
                                                start = Theme.dimensions.space.space100
                                            ),
                                        style = Theme.typography.normal300,
                                        text = stringResource(id = R.string.weather_temp_max),
                                        maxLines = 1,
                                        color = Theme.colors.onBackground,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                            Row {
                                item.minTemp?.let {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = Theme.dimensions.space.space200),
                                        style = Theme.typography.normal800,
                                        text = stringResource(
                                            id = R.string.temp_formatter,
                                            it
                                        ),
                                        maxLines = 1,
                                        color = Theme.colors.onBackground,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        modifier = Modifier
                                            .alpha(0.5F)
                                            .padding(
                                                top = Theme.dimensions.space.space400,
                                                start = Theme.dimensions.space.space100
                                            ),
                                        style = Theme.typography.normal300,
                                        text = stringResource(id = R.string.weather_temp_min),
                                        maxLines = 1,
                                        color = Theme.colors.onBackground,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column {
                FlowRow(
                    modifier = Modifier.padding(
                        top = Theme.dimensions.space.space500,
                        bottom = Theme.dimensions.space.space300,
                        start = Theme.dimensions.space.space400,
                        end = Theme.dimensions.space.space400,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Theme.dimensions.space.space300),
                ) {
                    item.rainfall?.let {
                        Tag(
                            iconId = R.drawable.ic_rain_filled,
                            text = stringResource(
                                id = R.string.precipitation_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_precipitation) }
                        )
                    }
                    item.cloudCover?.let {
                        Tag(
                            iconId = R.drawable.ic_cloud_cover_filled,
                            text = stringResource(
                                id = R.string.cloud_cover_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_cloud_cover) }
                        )
                    }
                    item.humidity?.let {
                        Tag(
                            iconId = R.drawable.ic_humidity_filled,
                            text = stringResource(
                                id = R.string.humidity_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_humidity) }
                        )
                    }
                    item.pressure?.let {
                        Tag(
                            iconId = R.drawable.ic_pressure,
                            text = stringResource(
                                id = R.string.pressure_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_pressure) }
                        )
                    }
                    item.dewPoint?.let {
                        Tag(
                            iconId = R.drawable.ic_dew_point_filled,
                            text = stringResource(
                                id = R.string.dew_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_dew_point) }
                        )
                    }
                    item.visibility?.let {
                        Tag(
                            iconId = R.drawable.ic_visibility_filled,
                            text = stringResource(
                                id = R.string.visibility_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_visibility) }
                        )
                    }
                    item.windSpeed?.let {
                        Tag(
                            iconId = R.drawable.ic_wind_flag_filled,
                            text = stringResource(
                                id = R.string.wind_formatter,
                                it,
                                item.windDir?.let { ", $it°" } ?: ""
                            ),
                            onClick = { showHint(R.string.hint_wind) }
                        )
                    }
                    item.sunshine?.let {
                        Tag(
                            iconId = R.drawable.ic_sunshine_filled,
                            text = stringResource(
                                id = R.string.sunshine_formatter,
                                it
                            ),
                            onClick = { showHint(R.string.hint_sunshine) }
                        )
                    }
                    item.solarIrradiation?.let {
                        Tag(
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
                ) {
                    items(item.items) { item ->
                        WeatherHour(
                            item = item
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LottieIcon(lottieFile: Int, size: Dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieFile))
    LottieAnimation(
        modifier = Modifier.size(size),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

//region preview
@Preview
@Preview(name = "WeatherNowDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherNowPreview() {
    ClassicTheme {
        WeatherNow(
            item = previewItemWeatherUiModelNow, showHint = {}, dismissSearchTextField = {}
        )
    }
}
//endregion preview
