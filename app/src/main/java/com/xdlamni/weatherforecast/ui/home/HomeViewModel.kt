package com.xdlamni.weatherforecast.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xdlamni.weatherforecast.api.repository.ForecastRepository
import com.xdlamni.weatherforecast.helpers.MapperHelpers
import com.xdlamni.weatherforecast.ui.model.DailyForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
): ViewModel() {

    private val _getWeatherForecast: MutableLiveData<ArrayList<DailyForecast>> = MutableLiveData<ArrayList<DailyForecast>>()
    val weatherForecast: LiveData<ArrayList<DailyForecast>> = _getWeatherForecast

    @SuppressLint("CheckResult")
    fun getDailyForecastAtLocation(lat: Double, lon: Double) {
        forecastRepository.getDailyForecast(lat, lon)
            .subscribe({forecastResponse ->
               _getWeatherForecast.value = MapperHelpers().mapDailyDtoToUIModel(forecastResponse)
            }, {
                Log.e("e", "${it.message}")
        })
    }
}