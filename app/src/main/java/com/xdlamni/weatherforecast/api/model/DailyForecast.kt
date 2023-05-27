package com.xdlamni.weatherforecast.api.model

data class DailyForecast (
    var name: String? = null,
    var icon: String? = null,
    var temp: Double? = null,
    var desciption: String? = null,
    var minTemp: String? = null,
    var maxTemp: String? = null,
)