package com.xdlamni.weatherforecast.helpers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import com.xdlamni.weatherforecast.api.dto.Forecast
import com.xdlamni.weatherforecast.ui.model.DailyForecast
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
fun ImageView?.loadWithGlide(tempIcon: String?) {
    this?.let {
        try {
            Glide.with(this.context)
                .load("${Constants.IMAGE_URL}${tempIcon}${Constants.IMG_EXTENSION}")
                .into(this)
        } catch (e: GlideException) {
            e.logRootCauses("GlideException")
        }
    }
}

fun DailyForecastDTO?.mapDailyDtoToUIModel(): ArrayList<DailyForecast> {
    val aryDailyForecast: ArrayList<DailyForecast> = arrayListOf()

    this?.forecasts?.mapTo(aryDailyForecast) { forecast ->
        DailyForecast(
            city = this.city?.name,
            tempIcon = forecast.weather?.get(0)?.icon,
            temp = forecast.main?.maxTemp?.convertToString(),
            day = forecast.time?.convertToDate(),
            description = forecast.weather?.get(0)?.description.formatDesc(forecast.main?.maxTemp?.convertToString(), forecast.main?.minTemp?.convertToString()),
            minTemp = forecast.main?.minTemp?.convertToString(),
            maxTemp = forecast.main?.maxTemp?.convertToString()
        )
    }
    return aryDailyForecast
}