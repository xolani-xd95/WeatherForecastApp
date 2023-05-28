package com.xdlamni.weatherforecast.helpers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun Double?.convertToString(): String {
    val roundedToInt = this?.roundToInt() ?: 0
    return "$roundedToInt°C"
}

fun Long?.convertToDate(): String {
    val epochToMillis = this?.let { epoch ->
        epoch * 1000L
    }

    val sdFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dtFormat = Date(epochToMillis ?: 0)
    return sdFormat.format(dtFormat)
}