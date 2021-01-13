package com.bentley.localweather.data.api

import com.bentley.localweather.data.entity.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.metaweather.com/api/"
    }

    @GET("location/search")
    suspend fun getLocalWeatherInfo(
        @Query("query") areaName: String
    ): List<WeatherEntity>
}