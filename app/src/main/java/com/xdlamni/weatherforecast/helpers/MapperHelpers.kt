package com.xdlamni.weatherforecast.helpers

import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import com.xdlamni.weatherforecast.ui.model.DailyForecast

class MapperHelpers {

    fun mapDailyDtoToUIModel(dailyForecastDTO: DailyForecastDTO): ArrayList<DailyForecast>{
        val aryDailyForecast: ArrayList<DailyForecast> = arrayListOf()

        dailyForecastDTO.forecasts?.mapTo(aryDailyForecast) { forecast ->
            DailyForecast(
                city = dailyForecastDTO.city?.name,
                tempIcon = forecast.weather?.get(0)?.icon,
                temp = forecast.main?.maxTemp?.convertToString(),
                day = forecast.time?.convertToDate(),
                description = forecast.weather?.get(0)?.description,
                minTemp = forecast.main?.minTemp?.convertToString(),
                maxTemp = forecast.main?.maxTemp?.convertToString()
            )
        }

        return aryDailyForecast
    }
}