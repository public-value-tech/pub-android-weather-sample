package io.github.publicvaluetech.pubandroidweathersample.ui.classic.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.publicvaluetech.pubandroidweathersample.data.repository.DatastoreRepository
import io.github.publicvaluetech.pubandroidweathersample.domain.FetchWeatherUsecase
import io.github.publicvaluetech.pubandroidweathersample.domain.GeoLocationUsecase
import io.github.publicvaluetech.pubandroidweathersample.domain.ReverseGeoLocationUsecase
import io.github.publicvaluetech.pubandroidweathersample.domain.WeatherResult
import io.github.publicvaluetech.pubandroidweathersample.domain.successData
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.WeatherSubItem
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.toItems
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.toLocationEntity
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.toLocationListItem
import io.github.publicvaluetech.pubandroidweathersample.ui.classic.model.toLocationListItems
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassicWeatherViewModel @Inject constructor(
    private val fetchWeatherUsecase: FetchWeatherUsecase,
    private val geoLocationUsecase: GeoLocationUsecase,
    private val reverseGeoLocationUsecase: ReverseGeoLocationUsecase,
    private val datastoreRepository: DatastoreRepository,
) : ViewModel() {
    private val locationErrorCallback: ((Int) -> Unit) = { msgRes ->
        viewModelScope.launch { _snackbarMsgResId.emit(msgRes) }
    }

    private var _snackbarMsgResId = MutableSharedFlow<Int?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val snackbarMsgResId: Flow<Int?> = _snackbarMsgResId

    private var _currentWeatherLocation = MutableStateFlow(WeatherSubItem.LocationListItem.DEFAULT)

    private var _searchQuery = MutableStateFlow(TextFieldValue(WeatherSubItem.LocationListItem.DEFAULT.city))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery

    private val weatherViaLocationIdState =
        fetchWeatherUsecase.results.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WeatherResult.initial
        )
    val items = weatherViaLocationIdState.successData()
        .mapNotNull { it.toItems() }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )
    val isLoading = weatherViaLocationIdState.map { it.isLoading }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )
    val error = weatherViaLocationIdState.map { it.error }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private var _autoCompletionItems = MutableSharedFlow<List<WeatherSubItem.LocationListItem>>()
    val autoCompletionItems = merge(
        geoLocationUsecase.results
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = WeatherResult.initial
            )
            .successData()
            .mapNotNull { it.toLocationListItems() },
        _autoCompletionItems
    ).shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )

    init {
        // Initialize search with default location
        viewModelScope.launch {
            datastoreRepository.currentWeatherLocation.first().let {
                it.toLocationListItem().let { locationListItem ->
                    setCurrentWeatherLocation(locationListItem)
                    fetchWeatherUsecase(locationListItem.lat, locationListItem.lon)
                }
            }
        }

        // Initialize LocationManager
        reverseGeoLocationUsecase.let {
            it.locationErrorCallback = locationErrorCallback
            it.results.onEach { br24Result ->
                br24Result.data?.let { address ->
                    fetchWeatherUsecase(
                        address.lat,
                        address.lon
                    ).also {
                        setCurrentWeatherLocation(address.toLocationListItem())
                    }

                }
            }.launchIn(viewModelScope)
        }
    }

    private var debounceJob: Job? = null
    fun onSearchQueryChange(query: TextFieldValue) {
        _searchQuery.value = query
        if (query.text.isNotBlank()) {
            debounceJob?.cancel()
            debounceJob = viewModelScope.launch {
                delay(500)
                if (query.text.trim().length >= 2) {
                    geoLocationUsecase(query.text)
                } else {
                    _autoCompletionItems.emit(emptyList())
                }
            }
        }
    }

    fun onWeatherAutoCompletionResultClicked(item: WeatherSubItem.LocationListItem) {
        debounceJob?.cancel()
        viewModelScope.launch {
            _autoCompletionItems.emit(emptyList())
            fetchWeatherUsecase(item.lat, item.lon)
            setCurrentWeatherLocation(item)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            fetchWeatherUsecase(_currentWeatherLocation.value.lat, _currentWeatherLocation.value.lon)
        }
    }

    fun getCurrentGeoLocation() {
        viewModelScope.launch {
            reverseGeoLocationUsecase()
        }
    }

    override fun onCleared() {
        reverseGeoLocationUsecase.detach()
    }

    private fun setCurrentWeatherLocation(item: WeatherSubItem.LocationListItem) {
        _searchQuery.value = TextFieldValue(text = item.city, selection = TextRange(item.city.count()))
        _currentWeatherLocation.value = item
        saveWeatherLocationAsCurrentToDatastore(item)
    }

    private fun saveWeatherLocationAsCurrentToDatastore(currentWeatherLocation: WeatherSubItem.LocationListItem) {
        viewModelScope.launch {
            datastoreRepository.setCurrentWeatherLocation(
                currentWeatherLocation.toLocationEntity()
            )
        }
    }
}
