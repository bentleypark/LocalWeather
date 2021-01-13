package com.bentley.localweather.di

import com.bentley.localweather.data.api.ApiService
import com.bentley.localweather.data.WeatherRepositoryImpl
import com.bentley.localweather.data.mapper.WeatherMapper
import com.bentley.localweather.data.WeatherRepository
import com.bentley.localweather.data.mapper.ConsolidatedWeatherMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        weatherMapper: WeatherMapper,
        consolidatedWeatherMapper: ConsolidatedWeatherMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, weatherMapper, consolidatedWeatherMapper)
    }

    @Singleton
    @Provides
    fun provideWeatherMapper(): WeatherMapper {
        return WeatherMapper()
    }

    @Singleton
    @Provides
    fun provideconsolidatedWeatherWeatherMapper(): ConsolidatedWeatherMapper {
        return ConsolidatedWeatherMapper()
    }
}