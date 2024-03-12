package io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.screen

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import io.github.publicvaluetech.pubandroidweathersample.Design
import io.github.publicvaluetech.pubandroidweathersample.MainViewModel
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.SlideInAnimation
import io.github.publicvaluetech.pubandroidweathersample.ui.metaControlsView
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.component.WeatherLocation
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.component.WeatherNow
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.component.WeatherWeek
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.compose.component.errorView
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.model.WeatherSubItem
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.model.WeatherUiModel
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.PubTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.defaultContentWidth
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.theme.noRippleClickable
import io.github.publicvaluetech.pubandroidweathersample.ui.pub.viewmodel.PubWeatherViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun WeatherScreen(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    weatherViewModel: PubWeatherViewModel,
    scaffoldState: ScaffoldState,
) {
    val items by weatherViewModel.items.collectAsState(initial = emptyList())
    val autoCompletionItems by weatherViewModel.autoCompletionItems.collectAsState(initial = emptyList())
    val isLoading by weatherViewModel.isLoading.collectAsState()
    val error by weatherViewModel.error.collectAsState()
    val searchQuery by weatherViewModel.searchQuery.collectAsState()
    val currentLocale by mainViewModel.locale.collectAsState()
    val currentDesign by mainViewModel.design.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        weatherViewModel.snackbarMsgResId.collect {
            it?.let {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(it),
                    actionLabel = context.getString(R.string.weather_snackbar_ok)
                )
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()
    fun showHint(@StringRes id: Int) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.resources.getString(id),
            )
        }
    }

    WeatherScreen(
        modifier = modifier,
        items = items,
        autoCompletionItems = autoCompletionItems,
        isLoading = isLoading,
        error = error,
        scaffoldState = scaffoldState,
        currentLocale = currentLocale,
        currentDesign = currentDesign,
        onGetCurrentGeoLocation = { weatherViewModel.getCurrentGeoLocation() },
        searchQuery = searchQuery,
        onSearchRefresh = { weatherViewModel.refresh() },
        onAutoCompletionResultClicked = { weatherViewModel.onWeatherAutoCompletionResultClicked(it) },
        onSearchChange = { weatherViewModel.onSearchQueryChange(it) },
        showHint = { showHint(it) },
        switchLanguage = mainViewModel::switchLanguage,
        switchDesign = mainViewModel::switchDesign
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WeatherScreen(
    modifier: Modifier = Modifier,
    items: List<WeatherUiModel>,
    autoCompletionItems: List<WeatherSubItem.LocationListItem>,
    isLoading: Boolean,
    error: Throwable?,
    scaffoldState: ScaffoldState,
    currentLocale: Locale,
    currentDesign: Design,
    onGetCurrentGeoLocation: () -> Unit,
    searchQuery: TextFieldValue,
    onSearchRefresh: () -> Unit,
    onAutoCompletionResultClicked: (WeatherSubItem.LocationListItem) -> Unit,
    onSearchChange: (TextFieldValue) -> Unit,
    showHint: (Int) -> Unit,
    switchDesign: (Design) -> Unit,
    switchLanguage: (Locale) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = onSearchRefresh)
    val focusManager = LocalFocusManager.current
    val dismissSearchTextField: () -> Unit = {
        focusManager.clearFocus()
        autoCompletionItems.firstOrNull()?.let {
            onAutoCompletionResultClicked(it)
        }
    }
    Box(
        modifier = Modifier
            .noRippleClickable { dismissSearchTextField() }
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = Theme.dimensions.space.space500, bottom = Theme.dimensions.space.space500),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            metaControlsView(
                modifier = Modifier.defaultContentWidth(),
                currentLocale = currentLocale,
                currentDesign = currentDesign,
                switchDesign = switchDesign,
                switchLanguage = switchLanguage
            ).also {
                item { SlideInAnimation { it() } }
            }
            errorView(error = error, onErrorRetry = onSearchRefresh)?.let { item { it() } }
            items(items, contentType = { it.type }) { item ->
                SlideInAnimation {
                    when (item) {
                        is WeatherUiModel.Location -> WeatherLocation(
                            modifier = Modifier.defaultContentWidth(),
                            item = item,
                            autoCompletionItems = autoCompletionItems,
                            scaffoldState = scaffoldState,
                            onGetCurrentGeoLocation = onGetCurrentGeoLocation,
                            searchQuery = searchQuery,
                            onWeatherAutoCompletionResultClicked = onAutoCompletionResultClicked,
                            onSearchChange = onSearchChange,
                            dismissSearchTextField = dismissSearchTextField,
                        )

                        is WeatherUiModel.Now -> WeatherNow(
                            modifier = Modifier.defaultContentWidth(),
                            item = item,
                            showHint = showHint,
                            dismissSearchTextField = dismissSearchTextField,
                        )

                        is WeatherUiModel.Week -> WeatherWeek(
                            modifier = Modifier.defaultContentWidth(),
                            item = item,
                            showHint = showHint,
                            dismissSearchTextField = dismissSearchTextField,
                        )
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

//region preview
@Preview
@Preview(name = "WeatherScreenDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherScreenPreview() {
    PubTheme {
        WeatherScreen(
            items = previewItems,
            autoCompletionItems = previewWeatherSubItemsSearch,
            isLoading = false,
            error = null,
            scaffoldState = rememberScaffoldState(),
            currentLocale = Locale.ENGLISH,
            currentDesign = Design.CLASSIC,
            onGetCurrentGeoLocation = {},
            searchQuery = TextFieldValue(),
            onSearchRefresh = {},
            onAutoCompletionResultClicked = {},
            onSearchChange = {},
            showHint = {},
            switchDesign = {},
            switchLanguage = {}
        )
    }
}

val previewItemWeatherUiModelLocation = WeatherUiModel.Location(
    timestamp = "Heute - Mittwoch, 05.10.22"
)

val previewItemWeatherSubItemHour = WeatherSubItem.Hour("23:00", "clear-day", "clear-day", "14")

val previewItemWeatherSubItemsHour = listOf(
    WeatherSubItem.Hour("2:00", "clear-day", "clear-day", "12"),
    WeatherSubItem.Hour("5:00", "clear-day", "clear-day", "19"),
    WeatherSubItem.Hour("8:00", "clear-day", "clear-day", "10"),
    WeatherSubItem.Hour("11:00", "clear-day", "clear-day", "24"),
    WeatherSubItem.Hour("14:00", "clear-day", "clear-day", "-1"),
    WeatherSubItem.Hour("17:00", "clear-day", "clear-day", "23"),
    WeatherSubItem.Hour("20:00", "clear-day", "clear-day", "19"),
    WeatherSubItem.Hour("23:00", "clear-day", "clear-day", "14")
)

val previewItemWeatherUiModelNow = WeatherUiModel.Now(
    icon = "clear-day",
    condition = "clear-day",
    actualTemp = "-15",
    maxTemp = "24",
    minTemp = "18",
    rainfall = "0,0",
    windSpeed = "14",
    windDir = "NW",
    humidity = "87",
    dewPoint = "2",
    visibility = "14536",
    solarIrradiation = "0.48",
    sunshine = "2100",
    pressure = "1015.1",
    cloudCover = "12.1",
    items = previewItemWeatherSubItemsHour
)

val previewWeatherSubItemsSearch = listOf(
    WeatherSubItem.LocationListItem(
        "81234",
        "Musterstadt",
        "Musteringen",
        "Musterbayern"
    ),
    WeatherSubItem.LocationListItem(
        "81235",
        "Testerstadt",
        "Testeringen",
        "Testerbayern in Garmisch-Partenkirchen"
    ),
    WeatherSubItem.LocationListItem(
        "81234",
        "Musterstadt",
        "Musteringen",
        "Musterbayern"
    )
)

val previewItemWeatherSubItemWeekDay = WeatherSubItem.WeekDay(
    icon = "clear-day",
    condition = "clear-day",
    "Mo, 10.10",
    "16",
    "9",
    "39",
    "18",
    "22",
    "5",
    "78",
    "-5",
    "13451",
    "0.12",
    "1024",
    "1015.1",
    "80",
    previewItemWeatherSubItemsHour
)

val previewItemWeatherUiModelWeek = WeatherUiModel.Week(
    days = listOf(
        previewItemWeatherSubItemWeekDay,
        previewItemWeatherSubItemWeekDay,
        previewItemWeatherSubItemWeekDay,
        previewItemWeatherSubItemWeekDay,
        previewItemWeatherSubItemWeekDay
    )
)

private val previewItems: List<WeatherUiModel> = listOf(
    previewItemWeatherUiModelLocation,
    previewItemWeatherUiModelNow,
    previewItemWeatherUiModelWeek
)
//endregion preview
