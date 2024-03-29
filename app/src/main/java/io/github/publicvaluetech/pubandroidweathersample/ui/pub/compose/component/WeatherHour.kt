package io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.screen.previewItemWeatherSubItemHour
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.model.WeatherSubItem
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.PubTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.WeatherFormatter

@Composable
fun WeatherHour(modifier: Modifier = Modifier, item: WeatherSubItem.Hour) {
    val context = LocalContext.current
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier,
            style = Theme.typography.normal250,
            text = if (!LocalInspectionMode.current) WeatherFormatter.getDateStringVersion3(context, item.timestamp) else "12.00",
            maxLines = 1,
            color = Theme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        LottieIcon(WeatherFormatter.getLottieIcon(item.icon, item.condition), Theme.dimensions.size.mediumIcon)
        item.temp?.let {
            Text(
                modifier = modifier,
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
    }
}

//region preview
@Preview
@Preview(name = "WeatherHourDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherHourPreview() {
    PubTheme {
        WeatherHour(item = previewItemWeatherSubItemHour)
    }
}
//endregion preview
