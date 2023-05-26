package com.xdlamni.weatherforecast.api.model

import com.google.gson.annotations.SerializedName

data class Main (
    @SerializedName("temp") val temperature: Double?,
    @SerializedName("feels_like") val humanTemp: Double?,
    @SerializedName("temp_min") val minTemp: Double?,
    @SerializedName("temp_max") val maxTemp: Double?,
    @SerializedName("pressure") val defaultSeaLevelAP: Double?,
    @SerializedName("sea_level") val seaLevelAP: Double?,
    @SerializedName("grnd_level") val groundLevelAP: Double?,
    @SerializedName("humidity") val humidity: Double?
//    @SerializedName("temp_kf") val Temperature: Double?,
)