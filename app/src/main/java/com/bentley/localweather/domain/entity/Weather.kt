package com.bentley.localweather.domain.entity

data class Location(
    val title: String,
    val id: Int
)

data class WeatherInfo(
    val location: Location,
    val weatherInfo: ConsolidatedWeather
)

data class ConsolidatedWeather(
    val weatherState: String,
    val weatherStateAbbr: String,
    val date: String,
    val temp: Int,
    val humidity: Float,
)
