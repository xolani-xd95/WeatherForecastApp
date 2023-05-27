package com.xdlamni.weatherforecast.api.model

import com.google.gson.annotations.SerializedName

data class City (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("coord") var coordinate: Coordinate? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("population") var population: Int? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
    @SerializedName("timezone") var timezone: Int? = null
)