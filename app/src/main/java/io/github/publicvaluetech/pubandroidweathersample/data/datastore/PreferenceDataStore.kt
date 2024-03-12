package io.github.publicvaluetech.pubandroidweathersample.data.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceDataStore {
    suspend fun <T> storePref(pref: Pref<T>, value: T)
    fun <T> loadPref(pref: Pref<T>): Flow<T?>
    fun <T> loadPref(pref: Pref<T>, default: T): Flow<T>
    suspend fun <T> removePref(pref: Pref<T>)
}
