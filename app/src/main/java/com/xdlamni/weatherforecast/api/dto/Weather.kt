package com.xdlamni.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class Weather (
    @SerializedName("id") val id: Int?,
    @SerializedName("main") val type: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)