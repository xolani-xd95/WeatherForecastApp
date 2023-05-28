package com.xdlamni.weatherforecast.ui.model

data class DailyForecast(
    var city: String? = null,
    var tempIcon: String? = null,
    var temp: String? = null,
    var day: String? = null,
    var description: String? = null,
    var minTemp: String? = null,
    var maxTemp: String? = null,
)