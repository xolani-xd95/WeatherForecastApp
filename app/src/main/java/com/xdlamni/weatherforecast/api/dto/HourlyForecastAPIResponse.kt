package com.xdlamni.weatherforecast.api.dto

import com.google.gson.annotations.SerializedName

data class HourlyForecastAPIResponse (
    @SerializedName("cod") val cod: Int? = null,
    @SerializedName("message") val message: Int? = null,
    @SerializedName("cnt") val count: Int? = null,
    @SerializedName("list") val forecasts: ArrayList<Forecast>? = null,
    @SerializedName("city") val city: City? = null
)