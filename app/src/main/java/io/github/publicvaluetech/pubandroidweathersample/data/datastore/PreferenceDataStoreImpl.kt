package io.github.publicvaluetech.pubandroidweathersample.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val moshi: Moshi,
) : PreferenceDataStore {

    override suspend fun <T> storePref(pref: Pref<T>, value: T) {
        dataStore.edit { preferences ->
            when (pref) {
                is IntPref -> preferences[intPreferencesKey(pref.key)] = value as Int
                is StringPref -> preferences[stringPreferencesKey(pref.key)] = value as String
                is BooleanPref -> preferences[booleanPreferencesKey(pref.key)] = value as Boolean
                is FloatPref -> preferences[floatPreferencesKey(pref.key)] = value as Float
                is ObjectPref -> preferences[stringPreferencesKey(pref.key)] =
                    moshi.adapter(pref.clazz).toJson(value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> loadPref(pref: Pref<T>): Flow<T?> {
        val key = createKey(pref)
        return dataStore.data.map { preferences ->
            val value = preferences[key]
            try {
                when (pref) {
                    is ObjectPref -> moshi.adapter(pref.clazz).fromJson(value.toString())
                    else -> value as? T
                }
            } catch (e: Exception) {
                null
            }
        }.distinctUntilChanged()
    }

    override fun <T> loadPref(pref: Pref<T>, default: T): Flow<T> =
        loadPref(pref).map { it ?: default }

    override suspend fun <T> removePref(pref: Pref<T>) {
        val key = createKey(pref)
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    private fun createKey(pref: Pref<*>): Preferences.Key<*> {
        return when (pref) {
            is IntPref -> intPreferencesKey(pref.key)
            is StringPref,
            is ObjectPref,
            -> stringSetPreferencesKey(pref.key)
            is BooleanPref -> booleanPreferencesKey(pref.key)
            is FloatPref -> floatPreferencesKey(pref.key)
        }
    }
}
