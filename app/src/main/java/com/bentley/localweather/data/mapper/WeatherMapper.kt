package com.bentley.localweather.data.mapper

import com.bentley.localweather.data.entity.WeatherEntity
import com.bentley.localweather.domain.entity.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() : EntityMapper<List<WeatherEntity>, List<Weather>> {
    override fun mapFromEntity(entity: List<WeatherEntity>): List<Weather> {
        return entity.map {
            with(it) {
                Weather(title, type, id, distance)
            }
        }
    }
}