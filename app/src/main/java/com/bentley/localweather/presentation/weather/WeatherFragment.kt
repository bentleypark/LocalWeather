package com.bentley.localweather.presentation.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bentley.localweather.databinding.FragmentWeatherBinding
import com.bentley.localweather.domain.entity.WeatherInfo
import com.bentley.localweather.utils.makeGone
import com.bentley.localweather.utils.makeVisible
import com.bentley.localweather.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                if (response.isNotEmpty()) {
                    weatherList = response

                    weatherListAdapter.updateList(weatherList)
                    lifecycleScope.launch {
                        delay(1500)
                        binding.apply {
                            tvTitle.makeVisible()
                            rvWeatherList.makeVisible()
                            progressCircular.makeGone()
                        }
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

            swipeLayout.setOnRefreshListener {
                lifecycleScope.launch {
                    rvWeatherList.makeGone()
                    viewModel.fetchWeatherInfo()

                    delay(3000)
                    swipeLayout.isRefreshing = false
                    rvWeatherList.makeVisible()
                }
            }
        }
    }

}