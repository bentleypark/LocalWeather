package com.bentley.localweather.domain

import com.bentley.localweather.data.WeatherRepository
import com.bentley.localweather.domain.entity.Weather
import com.bentley.localweather.domain.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun getLocalWeather(localName: String): Flow<DataState<List<Weather>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                emit(DataState.Success(weatherRepository.getLocalWeatherInfo(localName)))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
}