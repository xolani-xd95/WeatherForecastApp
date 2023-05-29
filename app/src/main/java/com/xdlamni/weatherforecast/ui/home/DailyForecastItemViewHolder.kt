package com.xdlamni.weatherforecast.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.xdlamni.weatherforecast.databinding.LayoutDailyForecastViewItemBinding
import com.xdlamni.weatherforecast.helpers.Constants.Companion.IMAGE_URL
import com.xdlamni.weatherforecast.helpers.loadWithGlide
import com.xdlamni.weatherforecast.ui.model.DailyForecast


class DailyForecastItemViewHolder(private val binding: LayoutDailyForecastViewItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun onBindView(item: DailyForecast) {
        binding.icnForecast.loadWithGlide("$IMAGE_URL${item.tempIcon}@2x.png")
        binding.txtForecastDt.text = item.day
        binding.txtDescForecast.text = item.description
    }
}