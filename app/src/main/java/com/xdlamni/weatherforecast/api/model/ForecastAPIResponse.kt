package com.xdlamni.weatherforecast.api.model

import com.google.gson.annotations.SerializedName

data class ForecastAPIResponse (
    @SerializedName("cod") val cod: Int?,
    @SerializedName("message") val message: Int?,
    @SerializedName("cnt") val count: Int?,
    @SerializedName("list") val forecasts: ArrayList<Forecast>?,
    @SerializedName("city") val city: City?,
)