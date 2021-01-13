package com.bentley.localweather.data.mapper

import com.bentley.localweather.data.entity.ConsolidatedWeatherEntity
import com.bentley.localweather.data.entity.WeatherEntity
import com.bentley.localweather.domain.entity.ConsolidatedWeather
import com.bentley.localweather.domain.entity.Location
import javax.inject.Inject

class WeatherMapper @Inject constructor() : EntityMapper<List<WeatherEntity>, List<Location>> {
    override fun mapFromEntity(entity: List<WeatherEntity>): List<Location> {
        return entity.map {
            with(it) {
                Location(title, id)
            }
        }
    }
}

class ConsolidatedWeatherMapper @Inject constructor() :
    EntityMapper<List<ConsolidatedWeatherEntity>, ConsolidatedWeather> {
    override fun mapFromEntity(entity: List<ConsolidatedWeatherEntity>): ConsolidatedWeather {
        return entity.map {
            with(it) {
                ConsolidatedWeather(weatherState, weatherStateAbbr, date, temp.toInt(), humidity)
            }
        }[0]
    }
}