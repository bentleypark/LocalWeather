package com.bentley.localweather.data

import com.bentley.localweather.data.api.ApiService
import com.bentley.localweather.data.mapper.ConsolidatedWeatherMapper
import com.bentley.localweather.data.mapper.WeatherMapper
import com.bentley.localweather.domain.entity.ConsolidatedWeather
import com.bentley.localweather.domain.entity.Location
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val weatherMapper: WeatherMapper,
    private val consolidatedWeatherMapper: ConsolidatedWeatherMapper
) : WeatherRepository {
    override suspend fun searchLocalInfo(searchKeyword: String): List<Location> {
        return weatherMapper.mapFromEntity(apiService.searchLocalInfo(searchKeyword))
    }

    override suspend fun getLocalWeatherInfoByDate(
        id: Int,
        day: Int
    ): ConsolidatedWeather {
        return consolidatedWeatherMapper.mapFromEntity(apiService.getLocalWeatherByDate(id, day))
    }
}