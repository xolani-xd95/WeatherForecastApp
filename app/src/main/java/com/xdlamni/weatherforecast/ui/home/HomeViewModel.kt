package com.xdlamni.weatherforecast.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.xdlamni.weatherforecast.api.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: ForecastRepository
): ViewModel() {

    fun getWeather() {
        repo.getWeather()
            .subscribe({
                Log.w("x", "$it")
            }, {
                Log.e("e", "${it.message}")
        })

    }
}