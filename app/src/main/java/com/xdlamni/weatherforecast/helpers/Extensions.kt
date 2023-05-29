package com.xdlamni.weatherforecast.helpers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun Double?.convertToString(): String {
    val roundedToInt = this?.roundToInt() ?: 0
    return "$roundedToInt°"
}

fun Long?.convertToDate(): String {
    val epochToMillis = this?.let { epoch ->
        epoch * 1000L
    }
    val sdFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val calender = Calendar.getInstance()
    val currentDayOfWeek = sdFormat.format(calender.time)

    val dtFormat = Date(epochToMillis ?: 0)
    val dayOfWeekForForecast = sdFormat.format(dtFormat)

    return if (dayOfWeekForForecast.equals(currentDayOfWeek)) "Today" else dayOfWeekForForecast
}

fun String?.formatDesc(maxTemp: String?, minTemp: String?): String {
    val formattedDesc = "$this $maxTemp / $minTemp"
    return formattedDesc.replaceFirstChar { it.uppercase() }
}
fun ImageView?.loadWithGlide(imageUrl: String) {
    this?.let {
        try {
            Glide.with(this.context)
                .load(imageUrl)
                .into(this)
        } catch (e: GlideException) {
            e.logRootCauses("GlideException")
        }
    }
}