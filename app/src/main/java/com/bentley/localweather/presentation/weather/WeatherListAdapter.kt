package com.bentley.localweather.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bentley.localweather.databinding.ItemWeatherLnfoBinding
import com.bentley.localweather.domain.entity.WeatherInfo

class WeatherListAdapter(private val list: MutableList<WeatherInfo>) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    private lateinit var binding: ItemWeatherLnfoBinding

    class WeatherListViewHolder(private val binding: ItemWeatherLnfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherInfo) {
            binding.apply {
                today.apply {

                }

                tomorrow.apply {

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

    override fun getItemCount() = list.size
}