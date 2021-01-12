package com.bentley.localweather.data

import com.bentley.localweather.data.entity.WeatherEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.metaweather.com/api/"
    }

    @GET("api/location/search")
    fun getLocalWeatherInfo(
        @Query("query") areaName: String
    ): Single<List<WeatherEntity>>
}