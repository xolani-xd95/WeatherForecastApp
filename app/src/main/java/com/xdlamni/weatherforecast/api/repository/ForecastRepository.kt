package com.xdlamni.weatherforecast.api.repository

import com.xdlamni.weatherforecast.api.ApiResponse
import com.xdlamni.weatherforecast.api.SafeApiCall
import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import com.xdlamni.weatherforecast.api.service.ForecastApi
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastApi: ForecastApi
): SafeApiCall() {
    suspend fun getDailyForecast(lat: Double, lon: Double): ApiResponse<DailyForecastDTO> {
        return safeApiCall { forecastApi.getDailyForecast(lat, lon) }
    }
}