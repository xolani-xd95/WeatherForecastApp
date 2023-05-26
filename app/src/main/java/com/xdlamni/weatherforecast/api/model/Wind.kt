package com.xdlamni.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class Wind (
    @SerializedName("speed") val speed: Double?,
    @SerializedName("deg") val direction: Double?,
    @SerializedName("gust") val gust: Double?
)