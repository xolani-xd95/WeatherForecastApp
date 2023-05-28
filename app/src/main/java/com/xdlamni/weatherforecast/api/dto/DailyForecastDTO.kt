package com.xdlamni.weatherforecast.api.dto

import com.google.gson.annotations.SerializedName

data class DailyForecastDTO (
    @SerializedName("cod") val cod: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("cnt") val count: Int? = null,
    @SerializedName("list") val forecasts: ArrayList<Forecast>? = null,
    @SerializedName("city") val city: City? = null
)