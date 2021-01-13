package com.bentley.localweather.data.api

import com.bentley.localweather.data.entity.ConsolidatedWeatherEntity
import com.bentley.localweather.data.entity.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.metaweather.com/api/"
    }

    @GET("location/search")
    suspend fun searchLocalInfo(
        @Query("query") searchKeyword: String
    ): List<WeatherEntity>

    @GET("location/{woeid}/2021/1/{day}")
    suspend fun getLocalWeatherByDate(
        @Path("woeid") id: Int,
        @Path("day") day: Int
    ): List<ConsolidatedWeatherEntity>
}