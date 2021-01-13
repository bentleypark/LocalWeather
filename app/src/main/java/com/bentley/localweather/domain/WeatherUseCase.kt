package com.bentley.localweather.domain

import com.bentley.localweather.data.WeatherRepository
import com.bentley.localweather.domain.entity.ConsolidatedWeather
import com.bentley.localweather.domain.entity.Location
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun searchLocalInfo(searchKeyword: String): List<Location> {
        return weatherRepository.searchLocalInfo(searchKeyword)
    }

    suspend fun getConsolidatedWeather(id: Int, date: Int): ConsolidatedWeather {
        return withContext(Dispatchers.Default) {
//            val weatherList = mutableListOf<ConsolidatedWeather>()
//            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//            val deferredList = ArrayList<Deferred<*>>()

//            deferredList.add(async {
//                weatherList.add(weatherRepository.getLocalWeatherInfoByDate(id, day))
//            })
//            deferredList.add(async {
//                weatherList.add(weatherRepository.getLocalWeatherInfoByDate(id, day + 1))
//            })
//            deferredList.joinAll()

            return@withContext weatherRepository.getLocalWeatherInfoByDate(id, date)
        }
    }
}