package com.xdlamni.weatherforecast.api.model

import com.google.gson.annotations.SerializedName

data class Sys (
    @SerializedName("pod") val partOfDay: String? // n or d make this ENUM
)