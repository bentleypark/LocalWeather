package com.bentley.localweather.domain

import com.bentley.localweather.domain.entity.Weather
import io.reactivex.Single

interface WeatherRepository {
    fun getLocalWeatherInfo(areaName: String): Single<List<Weather>>
}