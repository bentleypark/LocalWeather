package com.bentley.localweather.data.entity

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val type: String,
    @SerializedName("woeid")
    val id: Int,
    @SerializedName("distance")
    val distance: Int
)

data class ConsolidatedWeatherEntity(
    @SerializedName("id")
    val id: Double,
    @SerializedName("weather_state_name")
    val weatherState: String,
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,
    @SerializedName("applicable_date")
    val date: String,
    @SerializedName("the_temp")
    val temp: Double,
    @SerializedName("humidity")
    val humidity: Float
)