package com.bentley.localweather.data

import com.bentley.localweather.domain.WeatherRepository

class WeatherRepositoryImpl constructor(
    private val apiService: ApiService
) : WeatherRepository {
}