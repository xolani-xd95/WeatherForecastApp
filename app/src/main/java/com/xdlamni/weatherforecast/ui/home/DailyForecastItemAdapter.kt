package com.xdlamni.weatherforecast.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xdlamni.weatherforecast.databinding.LayoutDailyForecastViewItemBinding
import com.xdlamni.weatherforecast.ui.model.DailyForecast

class DailyForecastItemAdapter(
    private var dataSet: ArrayList<DailyForecast>
): RecyclerView.Adapter<DailyForecastItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyForecastItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutDailyForecastViewItemBinding.inflate(layoutInflater, parent, false)
        return DailyForecastItemViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: DailyForecastItemViewHolder, position: Int) {
        val forecast = dataSet[position]
        holder.onBindView(forecast)
    }
}