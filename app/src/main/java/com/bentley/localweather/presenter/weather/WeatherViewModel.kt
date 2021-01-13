package com.bentley.localweather.presenter.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bentley.localweather.domain.WeatherUseCase
import com.bentley.localweather.domain.entity.Weather
import com.bentley.localweather.domain.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WeatherViewModel @ViewModelInject
constructor(private val weatherRepository: WeatherUseCase) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<Weather>>>()
    val dataState: LiveData<DataState<List<Weather>>> get() = _dataState

    fun getWeatherInfo() {
        viewModelScope.launch {
            weatherRepository.getLocalWeather("se")
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}