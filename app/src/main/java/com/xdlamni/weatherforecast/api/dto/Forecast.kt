package com.xdlamni.weatherforecast.api.dto

import com.google.gson.annotations.SerializedName
import com.xdlamni.weatherforecast.model.Weather
import com.xdlamni.weatherforecast.model.Wind

data class Forecast (
    @SerializedName("dt") val time: Long?,
    @SerializedName("main", alternate = ["temp"]) val main: Main?,
    @SerializedName("weather") val weather: ArrayList<Weather>?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("visibility") val visibility: Double?,
    @SerializedName("pop") val propOfPrecipitation: Double?, // The values of the parameter vary between 0 and 1, where 0 is equal to 0%, 1 is equal to 100%
    @SerializedName("rain") val rain: Double?,
    @SerializedName("snow") val snow: Snow?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("dt_txt") val timeOfForecast: String?,
)