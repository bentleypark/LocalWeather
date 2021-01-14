package com.bentley.localweather.presentation.weather

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bentley.localweather.R
import com.bentley.localweather.databinding.ItemWeatherLnfoBinding
import com.bentley.localweather.domain.entity.WeatherInfo
import timber.log.Timber
import java.util.*

class WeatherListAdapter(private val list: MutableList<WeatherInfo>) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    private lateinit var binding: ItemWeatherLnfoBinding


    class WeatherListViewHolder(private val binding: ItemWeatherLnfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: WeatherInfo) {
            val current = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            binding.apply {
                if (tvTitle.text.isEmpty()) {
                    tvTitle.text = item.location.title
                }

                if (item.weatherInfo.date == current) {
                    today.apply {
                        ivWeather.load(
                            findWeatherImage(
                                ivWeather.context,
                                item.weatherInfo.weatherStateAbbr
                            )
                        )
                        tvWeatherAbbr.text = item.weatherInfo.weatherState
                        tvTemp.text = "${item.weatherInfo.temp} ℃"
                        tvHumidity.text = "${item.weatherInfo.humidity} %"
                    }
                } else {
                    tomorrow.apply {
                        ivWeather.load(
                            findWeatherImage(
                                ivWeather.context,
                                item.weatherInfo.weatherStateAbbr
                            )
                        )
                        tvWeatherAbbr.text = item.weatherInfo.weatherState
                        tvTemp.text = "${item.weatherInfo.temp} ℃"
                        tvHumidity.text = "${item.weatherInfo.humidity} %"
                    }
                }
            }
        }

        fun findWeatherImage(context: Context, weatherAbbr: String): Drawable? {
            return when (weatherAbbr) {
                CLEAR -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_clear, null)
                }
                LIGHT_CLOUD -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_light_cloud, null)
                }
                HEAVY_CLOUD -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_heavy_cloud, null)
                }
                SHOWERS -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_showers, null)
                }
                LIGHT_RAIN -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_light_rain, null)
                }
                HEAVY_RAIN -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_heavy_rain, null)
                }
                THUNDERSTORM -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_thunderstom, null)
                }
                HAIL -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_hail, null)
                }
                SNOW -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_snow, null)
                }
                else -> {
                    ResourcesCompat.getDrawable(context.resources, R.drawable.ic_sleet, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemWeatherLnfoBinding.inflate(layoutInflater)
        return WeatherListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: WeatherListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            Timber.d("payload $payloads")
            val tomorrowWeatherInfo = payloads[0] as List<WeatherInfo>
            holder.bind(tomorrowWeatherInfo[position])

        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount() = list.size

    fun addList(newList: MutableList<WeatherInfo>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    companion object {
        const val CLEAR = "c"
        const val LIGHT_CLOUD = "lc"
        const val HEAVY_CLOUD = "hc"
        const val SHOWERS = "s"
        const val LIGHT_RAIN = "lr"
        const val HEAVY_RAIN = "hr"
        const val THUNDERSTORM = "t"
        const val HAIL = "h"
        const val SLEET = "sl"
        const val SNOW = "sn"
    }

}