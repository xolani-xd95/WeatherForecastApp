package com.xdlamni.weatherforecast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdlamni.weatherforecast.api.ApiResponse
import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import com.xdlamni.weatherforecast.api.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
): ViewModel() {

    private val _getLoadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean> = _getLoadingState

    private val _getWeatherForecast: MutableLiveData<ApiResponse<DailyForecastDTO>> = MutableLiveData<ApiResponse<DailyForecastDTO>>()
    val weatherForecast: LiveData<ApiResponse<DailyForecastDTO>> = _getWeatherForecast

    fun getDailyForecastAtLocation(lat: Double, lon: Double) = viewModelScope.launch {
        _getLoadingState.value = true

        val response = forecastRepository.getDailyForecast(lat, lon)
        _getLoadingState.value = false
        _getWeatherForecast.value = response
    }
}