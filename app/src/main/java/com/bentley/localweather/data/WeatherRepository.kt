package com.bentley.localweather.data

import com.bentley.localweather.domain.entity.ConsolidatedWeather
import com.bentley.localweather.domain.entity.Location

interface WeatherRepository {
    suspend fun searchLocalInfo(searchKeyword: String): List<Location>

    suspend fun getLocalWeatherInfoByDate(id: Int, day: Int): ConsolidatedWeather
}