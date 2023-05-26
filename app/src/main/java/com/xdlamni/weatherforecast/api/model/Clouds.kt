package com.xdlamni.weatherforecast.api.model

import com.google.gson.annotations.SerializedName

data class Clouds (
    @SerializedName("all") val cloudiness: Double?
)