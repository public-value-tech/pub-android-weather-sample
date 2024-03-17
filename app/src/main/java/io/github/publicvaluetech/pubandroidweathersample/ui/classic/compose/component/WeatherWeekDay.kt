package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen.previewItemWeatherSubItemWeekDay
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherSubItem
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.WeatherFormatter

@Composable
fun WeatherWeekDay(
    modifier: Modifier = Modifier,
    item: WeatherSubItem.WeekDay,
    showIndicator: Boolean,
) {
    val bgColor = Theme.colors.primary
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(Theme.dimensions.size.cardCornerRadius),
        backgroundColor = if (showIndicator) bgColor else Color.Transparent,
        elevation = Theme.dimensions.space.space0,
        modifier = modifier
            .drawBehind {
                val trianglePath = Path().apply {
                    moveTo(x = size.width / 2F, y = size.height + density * 15f)
                    lineTo(x = size.width / 2F + density * 7.5f, y = size.height)
                    lineTo(x = size.width / 2F - density * 7.5f, y = size.height)
                }
                drawPath(
                    color = if (showIndicator) bgColor else Color.Transparent,
                    path = trianglePath,
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(
                    top = Theme.dimensions.space.space300,
                    start = Theme.dimensions.space.space300,
                    end = Theme.dimensions.space.space300
                ),
                style = Theme.typography.normal250,
                text = if (!LocalInspectionMode.current) WeatherFormatter.getDateStringVersion2(
                    context,
                    item.timestamp
                ) else "Mo, 24.04",
                maxLines = 1,
                color = Theme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            LottieIcon(WeatherFormatter.getLottieIcon(item.icon, item.condition), Theme.dimensions.size.bigIcon)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                item.maxTemp?.let {
                    Text(
                        modifier = Modifier.padding(Theme.dimensions.space.space200),
                        style = Theme.typography.normal500,
                        text = stringResource(
                            id = R.string.temp_formatter,
                            it
                        ),
                        maxLines = 1,
                        color = Theme.colors.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
                item.minTemp?.let {
                    Text(
                        modifier = Modifier.padding(Theme.dimensions.space.space200),
                        style = Theme.typography.normal600,
                        text = stringResource(
                            id = R.string.temp_formatter,
                            it
                        ),
                        maxLines = 1,
                        color = Theme.colors.onBackground.copy(0.5F),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

//region preview
@Preview
@Preview(name = "WeatherWeekDayDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherWeekDayPreview() {
    ClassicTheme {
        WeatherWeekDay(item = previewItemWeatherSubItemWeekDay, showIndicator = true)
    }
}
//endregion preview
