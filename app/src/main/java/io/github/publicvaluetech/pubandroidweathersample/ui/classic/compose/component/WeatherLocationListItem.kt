package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherSubItem
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme

@Composable
fun WeatherLocationListItem(
    modifier: Modifier = Modifier,
    item: WeatherSubItem.LocationListItem,
    onWeatherAutoCompletionResultClicked: (WeatherSubItem.LocationListItem) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(
                vertical = Theme.dimensions.space.space200
            ),
        backgroundColor = Theme.colors.background,
        shape = RoundedCornerShape(Theme.dimensions.size.size0),
        elevation = Theme.dimensions.space.space0
    ) {
        Text(
            modifier = modifier
                .clickable { onWeatherAutoCompletionResultClicked(item) }
                .fillMaxWidth()
                .padding(
                    top = Theme.dimensions.space.space400,
                    bottom = Theme.dimensions.space.space400,
                    start = Theme.dimensions.space.space400,
                    end = Theme.dimensions.space.space300
                ),
            text = item.label,
            style = Theme.typography.weatherLocationListItem.copy(color = Theme.colors.onBackground)
        )
    }
}

@Composable
fun NoWeatherLocationListItemFound(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = Modifier) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = Theme.dimensions.space.space300,
                    bottom = Theme.dimensions.space.space300,
                    start = Theme.dimensions.space.space400,
                    end = Theme.dimensions.space.space300
                ),
            text = text,
            style = Theme.typography.weatherLocationListItem.copy(color = Theme.colors.onBackground)
        )
    }
}
