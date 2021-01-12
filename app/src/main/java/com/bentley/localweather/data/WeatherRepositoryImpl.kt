package com.bentley.localweather.data

import com.bentley.localweather.data.mapper.WeatherMapper
import com.bentley.localweather.domain.WeatherRepository
import com.bentley.localweather.domain.entity.Weather
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherRepositoryImpl constructor(
    private val apiService: ApiService,
    private val weatherMapper: WeatherMapper
) : WeatherRepository {
    override fun getLocalWeatherInfo(areaName: String): Single<List<Weather>> {
        return apiService.getLocalWeatherInfo(areaName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(weatherMapper::mapFromEntity)
    }
}