package com.bentley.localweather.di

import com.bentley.localweather.data.ApiService
import com.bentley.localweather.data.WeatherRepositoryImpl
import com.bentley.localweather.data.mapper.WeatherMapper
import com.bentley.localweather.domain.WeatherRepository
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
        weatherMapper: WeatherMapper
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, weatherMapper)
    }

    @Singleton
    @Provides
    fun provideWeatherMapper(): WeatherMapper {
        return WeatherMapper()
    }
}