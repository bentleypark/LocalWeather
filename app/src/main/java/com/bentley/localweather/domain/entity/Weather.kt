package com.bentley.localweather.domain.entity

data class Location(
    val title: String,
    val id: Int
)

data class WeatherInfo(
    val location: Location,
    val weatherInfo: MutableList<ConsolidatedWeather>
)

data class ConsolidatedWeather(
    val id: Double ,
    val weatherState: String ,
    val weatherStateAbbr: String,
    val date: Int,
    val temp: Int,
    val humidity: Int,
)
