package com.bentley.localweather.presentation.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bentley.localweather.domain.WeatherUseCase
import com.bentley.localweather.domain.entity.Location
import com.bentley.localweather.domain.entity.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel @ViewModelInject
constructor(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private var searchResult: List<Location> = emptyList()
    private var weatherList: MutableList<WeatherInfo> = mutableListOf()
    private val _weatherInfo = MutableLiveData<MutableList<WeatherInfo>>()
    val weatherInfo: LiveData<MutableList<WeatherInfo>> get() = _weatherInfo

    private var date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    init {
        searchLocalInfo()
    }

    private fun searchLocalInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherUseCase.searchLocalInfo(SEARCH_KEYWORD).let {
                searchResult = it
                fetchWeatherInfo()
            }
        }
    }

    fun fetchWeatherInfo() {
        if (weatherList.isNotEmpty()) {
            weatherList = mutableListOf()
        }

        searchResult.forEach { location ->
            getConsolidatedWeather(location)
        }
    }

    private fun getConsolidatedWeather(location: Location) {
        viewModelScope.launch(Dispatchers.Main) {
            val today = flowOf(weatherUseCase.getConsolidatedWeather(location.id, date))
            val tomorrow = flowOf(weatherUseCase.getConsolidatedWeather(location.id, date + 1))

            today.zip(tomorrow) { todayW, tomorrowW ->
                WeatherInfo(location, mutableListOf(todayW, tomorrowW))
            }.collect {
                weatherList.add(it)
            }

            if (weatherList.size == searchResult.size) {
                _weatherInfo.value =
                    weatherList.sortedBy { it.location.title }.toMutableList()
            }
        }
    }

    companion object {
        private const val SEARCH_KEYWORD = "se"
    }
}