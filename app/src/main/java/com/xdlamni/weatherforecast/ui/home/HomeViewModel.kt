package com.xdlamni.weatherforecast.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xdlamni.weatherforecast.api.model.ForecastAPIResponse
import com.xdlamni.weatherforecast.api.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
): ViewModel() {

    private val _getWeatherForecast: MutableLiveData<ForecastAPIResponse> = MutableLiveData<ForecastAPIResponse>()
    val weatherForecast: LiveData<ForecastAPIResponse> = _getWeatherForecast


    @SuppressLint("CheckResult")
    fun getCurrentForecastAtLocation(lat: Double, lon: Double) {
        forecastRepository.getCurrentForecast(lat, lon)
            .subscribe({forecastResponse ->
               _getWeatherForecast.value = forecastResponse
            }, {
                Log.e("e", "${it.message}")
        })
    }
    @SuppressLint("CheckResult")
    fun getDailyForecastAtLocation(lat: Double, lon: Double) {
        forecastRepository.getDailyForecast(lat, lon)
            .subscribe({forecastResponse ->
               _getWeatherForecast.value = forecastResponse
            }, {
                Log.e("e", "${it.message}")
        })
    }
}