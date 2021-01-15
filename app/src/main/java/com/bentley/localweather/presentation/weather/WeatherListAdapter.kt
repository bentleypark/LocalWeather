package com.bentley.localweather.presentation.weather

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bentley.localweather.R
import com.bentley.localweather.databinding.ItemWeatherInfoBinding
import com.bentley.localweather.databinding.ItemWeatherInfoHeaderBinding
import com.bentley.localweather.domain.entity.WeatherInfo
import java.util.*

class WeatherListAdapter(private val list: MutableList<WeatherInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemWeatherInfoBinding
    private lateinit var header: ItemWeatherInfoHeaderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ITEM -> {
                binding = ItemWeatherInfoBinding.inflate(layoutInflater)
                WeatherListViewHolder(binding)
            }
            TYPE_HEADER -> {
                header = ItemWeatherInfoHeaderBinding.inflate(layoutInflater)
                WeatherListHeaderViewHolder(header)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherListViewHolder -> {
                val item = list[position - HEADER_SIZE]
                holder.bind(item)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (holder) {
            is WeatherListViewHolder -> {
                if (payloads.isNotEmpty()) {
                    val tomorrowWeatherInfo = payloads[0] as List<WeatherInfo>
                    holder.bind(tomorrowWeatherInfo[position - 1])

                } else {
                    super.onBindViewHolder(holder, position, payloads)
                }
            }
        }
    }

    override fun getItemCount() = list.size + HEADER_SIZE

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    fun addList(newList: MutableList<WeatherInfo>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateList(newList: MutableList<WeatherInfo>) {
        val diffCallback = Diff(list, newList)
        val diffResult = calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class WeatherListViewHolder(private val binding: ItemWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: WeatherInfo) {
            binding.apply {

                if (tvTitle.text.isEmpty()) {
                    tvTitle.text = item.location.title
                }

                today.apply {
                    ivWeather.load(
                        findWeatherImage(
                            ivWeather.context,
                            item.weatherInfo[0].weatherStateAbbr
                        )
                    )

                    tvWeatherState.text = item.weatherInfo[0].weatherState
                    tvTemp.text = "${item.weatherInfo[0].temp} ℃"
                    tvHumidity.text = "${item.weatherInfo[0].humidity} %"
                }
                tomorrow.apply {
                    ivWeather.load(
                        findWeatherImage(
                            ivWeather.context,
                            item.weatherInfo[1].weatherStateAbbr
                        )
                    )
                    tvWeatherState.text = item.weatherInfo[1].weatherState
                    tvTemp.text = "${item.weatherInfo[1].temp} ℃"
                    tvHumidity.text = "${item.weatherInfo[1].humidity} %"
                }
            }
        }

        private fun findWeatherImage(context: Context, weatherAbbr: String): Drawable? {
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

    class WeatherListHeaderViewHolder(private val itemView: ItemWeatherInfoHeaderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        fun bind() {
        }
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
        const val SNOW = "sn"

        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val HEADER_SIZE = 1
    }
}