package io.github.publicvaluetech.pubandroidweathersample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.github.publicvaluetech.pubandroidweathersample.data.remote.api.WeatherApi
import io.github.publicvaluetech.pubandroidweathersample.data.remote.api.weatherApi
import io.github.publicvaluetech.pubandroidweathersample.data.repository.BrightSkyRepository
import io.github.publicvaluetech.pubandroidweathersample.data.repository.BrightSkyRepositoryImpl

@InstallIn(ViewModelComponent::class)
@Module
object WeatherRepositoryModuleDI {
    @Provides
    fun providesWeatherRepository(weatherApi: WeatherApi): BrightSkyRepository {
        return BrightSkyRepositoryImpl(weatherApi)
    }

    @Provides
    fun providesWeatherApiRemote(): WeatherApi {
        return weatherApi()
    }
}
