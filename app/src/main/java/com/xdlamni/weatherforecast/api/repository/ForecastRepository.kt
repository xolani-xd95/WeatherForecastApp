package com.xdlamni.weatherforecast.api.repository

import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import com.xdlamni.weatherforecast.api.service.ForecastApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastApi: ForecastApi
) {
    fun getDailyForecast(lat: Double, lon: Double): Observable<DailyForecastDTO> {
       return forecastApi.getDailyForecast(lat, lon)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
    }
}