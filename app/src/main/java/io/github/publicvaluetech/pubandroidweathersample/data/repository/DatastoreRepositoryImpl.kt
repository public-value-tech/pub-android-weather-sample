package io.github.publicvaluetech.pubandroidweathersample.data.repository

import io.github.publicvaluetech.pubandroidweathersample.Design
import io.github.publicvaluetech.pubandroidweathersample.data.datastore.ObjectPref
import io.github.publicvaluetech.pubandroidweathersample.data.datastore.PreferenceDataStore
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatastoreRepositoryImpl @Inject constructor(private val preferenceDataStore: PreferenceDataStore) :
    DatastoreRepository {

    override val currentWeatherLocation: Flow<LocationEntity> =
        preferenceDataStore.loadPref(currentWeatherLocationPref, LocationEntity.DEFAULT)

    override val currentDesign: Flow<Design> =
        preferenceDataStore.loadPref(currentDesignPref, Design.CLASSIC)

    override suspend fun setCurrentWeatherLocation(currentWeatherLocation: LocationEntity) =
        preferenceDataStore.storePref(currentWeatherLocationPref, currentWeatherLocation)

    override suspend fun setCurrentDesign(currentDesign: Design) {
        preferenceDataStore.storePref(currentDesignPref, currentDesign)
    }

    companion object {
        private val currentWeatherLocationPref = ObjectPref("current_weather_location", LocationEntity::class.java)
        private val currentDesignPref = ObjectPref("current_design", Design::class.java)
    }
}
