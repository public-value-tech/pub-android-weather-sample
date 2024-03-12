package io.github.publicvaluetech.pubandroidweathersample.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.publicvaluetech.pubandroidweathersample.data.datastore.PreferenceDataStore
import io.github.publicvaluetech.pubandroidweathersample.data.datastore.PreferenceDataStoreImpl
import io.github.publicvaluetech.pubandroidweathersample.data.repository.DatastoreRepository
import io.github.publicvaluetech.pubandroidweathersample.data.repository.DatastoreRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatastoreRepositoryModuleDI {
    private const val FILE_NAME = "WEATHER_APP_PREFS"

    @Singleton
    @Provides
    fun providesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(FILE_NAME) }
        )
    }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun providesPreferenceDataStore(
        dataStore: DataStore<Preferences>,
        moshi: Moshi,
    ): PreferenceDataStore = PreferenceDataStoreImpl(dataStore, moshi)

    @Provides
    fun providesDatastoreRepository(repository: DatastoreRepositoryImpl): DatastoreRepository {
        return repository
    }
}
