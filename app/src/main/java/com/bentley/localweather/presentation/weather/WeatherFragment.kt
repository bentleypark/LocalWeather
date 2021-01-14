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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

                    if (weatherListAdapter.itemCount == DEFAULT_LIST_SIZE) {
                        weatherListAdapter.addList(weatherList)

                        lifecycleScope.launch {
                            delay(2500)
                            binding.apply {
                                tvTitle.makeVisible()
                                rvWeatherList.makeVisible()
                                progressCircular.makeGone()
                            }
                        }
                    } else {
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

            swipeLayout.setOnRefreshListener {
                lifecycleScope.launch{
                    rvWeatherList.makeGone()
                    viewModel.fetchWeatherInfo()
                    delay(3000)
                    swipeLayout.isRefreshing = false
                    rvWeatherList.makeVisible()
                }
            }
        }
    }

    companion object {
        const val DEFAULT_LIST_SIZE = 1
    }

}