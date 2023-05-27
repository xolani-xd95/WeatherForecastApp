package com.xdlamni.weatherforecast.api.model

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null
)