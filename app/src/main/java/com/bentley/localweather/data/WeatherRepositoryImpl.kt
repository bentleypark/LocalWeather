package com.bentley.localweather.data

import com.bentley.localweather.data.api.ApiService
import com.bentley.localweather.data.mapper.WeatherMapper
import com.bentley.localweather.domain.entity.Weather
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val weatherMapper: WeatherMapper
) : WeatherRepository {
    override suspend fun getLocalWeatherInfo(areaName: String): List<Weather> {
        return weatherMapper.mapFromEntity(apiService.getLocalWeatherInfo(areaName))
    }
}