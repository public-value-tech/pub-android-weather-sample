package io.github.publicvaluetech.pubandroidweathersample.data.repository

import io.github.publicvaluetech.pubandroidweathersample.Design
import io.github.publicvaluetech.pubandroidweathersample.domain.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    val currentWeatherLocation: Flow<LocationEntity>
    val currentDesign: Flow<Design>
    suspend fun setCurrentWeatherLocation(currentWeatherLocation: LocationEntity)
    suspend fun setCurrentDesign(currentDesign: Design)
}
