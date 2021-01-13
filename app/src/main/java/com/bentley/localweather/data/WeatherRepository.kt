package com.bentley.localweather.data

import com.bentley.localweather.domain.entity.Weather

interface WeatherRepository {
    suspend fun getLocalWeatherInfo(areaName: String): List<Weather>
}