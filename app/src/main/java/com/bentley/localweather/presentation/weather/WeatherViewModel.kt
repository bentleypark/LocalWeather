package com.bentley.localweather.presentation.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bentley.localweather.domain.WeatherUseCase
import com.bentley.localweather.domain.entity.Location
import com.bentley.localweather.domain.entity.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
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
        if (date <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1)
            searchResult.forEach { location ->
                getConsolidatedWeather(location)
            } else {
            clear()
        }
    }

    private fun getConsolidatedWeather(location: Location) {
        viewModelScope.launch(Dispatchers.Main) {
            weatherUseCase.getConsolidatedWeather(location.id, date).let {
                weatherList.add(WeatherInfo(location, it))
            }

            if (weatherList.size == searchResult.size) {
                _weatherInfo.value = weatherList
            }
        }
    }

    fun fetchNextWeatherInfo() {
        date += 1
        weatherList = mutableListOf()

        fetchWeatherInfo()
    }

    private fun clear() {
        Timber.d("clear $date")
        date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        weatherList = mutableListOf()
        Timber.d("clear $date")
    }

    companion object {
        private const val SEARCH_KEYWORD = "se"
    }
}