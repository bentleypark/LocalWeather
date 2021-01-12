package com.bentley.localweather.domain.entity

data class Weather(
    val title: String,
    val type: String,
    val id: Int,
    val distance: Int
)
