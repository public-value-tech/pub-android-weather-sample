package io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.component

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import io.github.publicvaluetech.pubandroidweathersample.R
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen.previewItemWeatherUiModelLocation
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.compose.screen.previewWeatherSubItemsSearch
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherSubItem
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherUiModel
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.ClassicTheme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.Theme
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.WeatherFormatter
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.theme.noRippleClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherLocation(
    modifier: Modifier = Modifier,
    item: WeatherUiModel.Location,
    autoCompletionItems: List<WeatherSubItem.LocationListItem>,
    scaffoldState: ScaffoldState,
    onGetCurrentGeoLocation: () -> Unit,
    searchQuery: TextFieldValue,
    onWeatherAutoCompletionResultClicked: (WeatherSubItem.LocationListItem) -> Unit,
    onSearchChange: (TextFieldValue) -> Unit,
    dismissSearchTextField: () -> Unit,
) {
    var isTextFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationPermissionRequest = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            onGetCurrentGeoLocation()
        } else {
            coroutineScope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = context.resources.getString(R.string.permission_denied_explanation),
                    actionLabel = context.resources.getString(android.R.string.ok)
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        ContextCompat.startActivity(
                            context,
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + context.packageName)
                            ),
                            null
                        )
                    }
                }
            }
        }
    }
    Card(
        modifier = modifier
            .noRippleClickable { dismissSearchTextField() }
            .padding(
                top = Theme.dimensions.space.space500,
                bottom = Theme.dimensions.space.space250
            )
            .animateContentSize(),
        shape = RoundedCornerShape(Theme.dimensions.size.cardCornerRadius),
        backgroundColor = Theme.colors.background,
        elevation = Theme.dimensions.space.space0,
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(
                        start = Theme.dimensions.space.space400,
                        top = Theme.dimensions.space.space400
                    ),
                style = Theme.typography.normal400,
                text = if (!LocalInspectionMode.current) WeatherFormatter.getDateStringVersion1(
                    context,
                    item.timestamp
                ) else "Today - Saturday, 22.04.23",
                maxLines = 1,
                color = Theme.colors.onBackground,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Theme.dimensions.space.space400,
                        bottom = Theme.dimensions.space.space400,
                        end = Theme.dimensions.space.space400
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = {
                        onSearchChange(it)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = Theme.dimensions.space.space300)
                        .indicatorLine(
                            true,
                            false,
                            interactionSource,
                            TextFieldDefaults.textFieldColors(
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Theme.colors.error,
                                focusedIndicatorColor = Theme.colors.onBackground,
                                unfocusedIndicatorColor = Theme.colors.onBackground
                            ),
                            Theme.dimensions.size.weatherFocusedIndicator,
                            Theme.dimensions.size.weatherUnfocusedIndicator
                        )
                        .background(Color.Transparent)
                        .onFocusChanged { focusState ->
                            isTextFocused = focusState.isFocused
                        },
                    maxLines = 1,
                    singleLine = true,
                    textStyle =
                    Theme.typography.weatherLocationInputText.copy(color = Theme.colors.onBackground),
                    cursorBrush = SolidColor(Theme.colors.onBackground),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            autoCompletionItems.firstOrNull()?.let {
                                onWeatherAutoCompletionResultClicked(it)
                            }
                        }
                    ),
                    decorationBox = @Composable { innerTextField ->
                        Row(
                            modifier = Modifier
                                .height(Theme.dimensions.size.weatherTextFieldHeight)
                                .padding(Theme.dimensions.space.space0),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                innerTextField()
                            }
                            if (isTextFocused && searchQuery.text.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        onSearchChange(TextFieldValue(""))
                                    },
                                    modifier = Modifier
                                        .padding(Theme.dimensions.space.space0)
                                        .offset(x = Theme.dimensions.space.space300)
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_close),
                                        contentDescription = null,
                                        tint = Theme.colors.onBackground,
                                        modifier = Modifier.padding(Theme.dimensions.space.space0),
                                    )
                                }
                            }
                        }
                    },
                )
                IconButton(
                    onClick = {
                        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    },
                    modifier = Modifier
                        .size(Theme.dimensions.size.mediumIcon)
                        .padding(
                            start = Theme.dimensions.space.space300,
                            end = Theme.dimensions.space.space300
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        tint = Theme.colors.onBackground,
                        modifier = Modifier
                            .size(Theme.dimensions.size.smallIcon)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(
                        top = Theme.dimensions.space.space100,
                        bottom = Theme.dimensions.space.space100
                    )
                    .heightIn(max = (TextFieldDefaults.MinHeight / 2) * 6) // show 6 lines based on textHeight
            ) {
                if (autoCompletionItems.isEmpty() && searchQuery.text.count() > 2 && isTextFocused) {
                    noItemsFound {
                        NoWeatherLocationListItemFound(
                            text = stringResource(id = R.string.no_location_results)
                        )
                    }
                }
                items(autoCompletionItems) { item ->
                    WeatherLocationListItem(
                        item = item,
                        onWeatherAutoCompletionResultClicked = {
                            focusManager.clearFocus()
                            onWeatherAutoCompletionResultClicked(it)
                        }
                    )
                }
            }
        }
    }
}

private fun LazyListScope.noItemsFound(
    content: @Composable LazyItemScope.() -> Unit,
) {
    item(content = content)
}

//region preview
@Preview
@Preview(name = "WeatherLocationDarkPreview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherLocationPreview() {
    ClassicTheme {
        WeatherLocation(
            item = previewItemWeatherUiModelLocation,
            autoCompletionItems = previewWeatherSubItemsSearch,
            searchQuery = TextFieldValue(),
            onWeatherAutoCompletionResultClicked = {},
            onSearchChange = {},
            scaffoldState = rememberScaffoldState(),
            onGetCurrentGeoLocation = {},
            dismissSearchTextField = {}
        )
    }
}
//endregion preview
