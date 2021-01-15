package com.bentley.localweather.presentation.weather

import androidx.recyclerview.widget.DiffUtil
import com.bentley.localweather.domain.entity.WeatherInfo

class Diff(private val oldItems: List<WeatherInfo>, private val newItems: List<WeatherInfo>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition].weatherInfo
        val newItem = newItems[newItemPosition].weatherInfo

        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}