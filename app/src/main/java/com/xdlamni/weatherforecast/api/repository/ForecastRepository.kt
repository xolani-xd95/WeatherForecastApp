package com.xdlamni.weatherforecast.api.repository

import com.xdlamni.weatherforecast.api.model.ForecastAPIResponse
import com.xdlamni.weatherforecast.api.service.ForecastApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastApi: ForecastApi
) {
    fun getWeather(lat: Double, ): Observable<ForecastAPIResponse> {
       return forecastApi.getWeather(-28.201, 20.897)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
    }
}