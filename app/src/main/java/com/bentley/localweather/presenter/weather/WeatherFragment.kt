package com.bentley.localweather.presenter.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bentley.localweather.R
import com.bentley.localweather.databinding.FragmentWeatherBinding
import com.bentley.localweather.domain.entity.Weather
import com.bentley.localweather.domain.state.DataState
import com.bentley.localweather.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private var binding: FragmentWeatherBinding by viewLifecycle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv.text = "test"

        viewModel.apply {
            getWeatherInfo()
            dataState.observe(viewLifecycleOwner, { dataState ->
                when (dataState) {
                    is DataState.Success<List<Weather>> -> {

                    }
                    is DataState.Loading -> {

                    }
                    else -> {

                    }
                }
            })
        }
    }
}