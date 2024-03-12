package io.github.publicvaluetech.pubandroidweathersample

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.publicvaluetech.pubandroidweathersample.data.repository.DatastoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
) : ViewModel() {
    private var _design = MutableStateFlow(Design.CLASSIC)
    val design: StateFlow<Design> = _design

    private var _locale = MutableStateFlow(Locale.ENGLISH)
    val locale: StateFlow<Locale> = _locale

    init {
        viewModelScope.launch {
            datastoreRepository.currentDesign.first().let {
                _design.emit(it)
            }
        }
    }

    fun setInitialLocale(locale: Locale) {
        viewModelScope.launch {
            _locale.emit(locale)
        }
    }

    fun switchDesign(currentDesign: Design) {
        viewModelScope.launch {
            _design.emit(currentDesign)
            datastoreRepository.setCurrentDesign(currentDesign)
        }
    }

    fun switchLanguage(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale.language)).also {
            viewModelScope.launch {
                _locale.emit(locale)
            }
        }
    }
}

enum class Design {
    CLASSIC, PUB
}
