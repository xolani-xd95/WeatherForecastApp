package com.xdlamni.weatherforecast.api.service

import com.xdlamni.weatherforecast.api.model.ForecastAPIResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("forecast")
    fun getWeather(
    @Query("lat")lat: Double,
    @Query("lon")lon: Double
    ): Observable<ForecastAPIResponse>
}