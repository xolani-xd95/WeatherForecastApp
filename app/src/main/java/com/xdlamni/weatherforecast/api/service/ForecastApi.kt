package com.xdlamni.weatherforecast.api.service

import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("forecast/daily")
    fun getDailyForecast(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("cnt")cnt: Int = 5,
        @Query("units")units: String = "metric"
    ): Observable<DailyForecastDTO>
}