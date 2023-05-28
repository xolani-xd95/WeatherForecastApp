package com.xdlamni.weatherforecast.api.dto

import com.google.gson.annotations.SerializedName

data class Rain (
    @SerializedName("3h") val volume: Double?
)