package com.bentley.localweather.presentation.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bentley.localweather.databinding.FragmentWeatherBinding
import com.bentley.localweather.domain.entity.WeatherInfo
import com.bentley.localweather.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private var binding: FragmentWeatherBinding by viewLifecycle()
    private var weatherList = mutableListOf<WeatherInfo>()
    private lateinit var weatherListAdapter: WeatherListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserve()
        setUpView()
    }

    private fun setUpObserve() {
        viewModel.apply {
            weatherInfo.observe(viewLifecycleOwner, { response ->
                Timber.d(response.toString())
                if (response.isNotEmpty()) {
                    weatherList = response
                    fetchNextWeatherInfo()

                    Timber.d("list count ${weatherListAdapter.itemCount}")
                    if (weatherListAdapter.itemCount == 0) {
                        Timber.d("list add")
                        weatherListAdapter.addList(weatherList)
                    }
                    else {
                        Timber.d("list update")
                        weatherListAdapter.notifyItemRangeChanged(
                            0,
                            weatherListAdapter.itemCount,
                            weatherList
                        )
                    }
                }
            })
        }
    }

    private fun setUpView() {
        weatherListAdapter = WeatherListAdapter(weatherList)
        binding.apply {
            rvWeatherList.apply {
                adapter = weatherListAdapter
                setHasFixedSize(true)
            }
        }
    }

}