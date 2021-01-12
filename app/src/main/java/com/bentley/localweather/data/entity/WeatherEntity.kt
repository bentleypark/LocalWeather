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