package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Root(

	@field:SerializedName("current")
	val current: Current?=null ,

	@field:SerializedName("minutely")
	val minutely: List<MinutelyItem?> =emptyList() ,

	@field:SerializedName("timezone")
	val timezone: String?=null ,

	@field:SerializedName("timezone_offset")
	val timezoneOffset: Int ?=null ,

	@field:SerializedName("daily")
	val daily: List<DailyItem> = emptyList() ,

	@field:SerializedName("lon")
	val lon: Double?=null,

	@field:SerializedName("hourly")
	val hourly: List<HourlyItem> = emptyList() ,

	@field:SerializedName("lat")
	val lat: Double?=null ,

	@field:SerializedName("alerts")
	var alerts: List<AlertsItem> = emptyList()
)

data class HourlyItem(

	@field:SerializedName("temp")
	val temp: Double,

	@field:SerializedName("visibility")
	val visibility: Int,

	@field:SerializedName("uvi")
	val uvi: Double,

	@field:SerializedName("pressure")
	val pressure: Int,

	@field:SerializedName("clouds")
	val clouds: Int,

	@field:SerializedName("feels_like")
	val feelsLike: Double,

	@field:SerializedName("wind_gust")
	val windGust: Double,

	@field:SerializedName("dt")
	val dt: Int,

	@field:SerializedName("pop")
	val pop: Double,

	@field:SerializedName("wind_deg")
	val windDeg: Double,

	@field:SerializedName("dew_point")
	val dewPoint: Double,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("wind_speed")
	val windSpeed: Double,

	@field:SerializedName("rain")
	val rain: Rain
)

data class Rain(

	@field:SerializedName("1h")
	val jsonMember1h: Any 
)

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("main")
	val main: String ,

	@field:SerializedName("id")
	val id: Int 
)

data class FeelsLike(

	@field:SerializedName("eve")
	val eve: Double ,

	@field:SerializedName("night")
	val night: Double ,

	@field:SerializedName("day")
	val day: Double ,

	@field:SerializedName("morn")
	val morn:  Double
)

data class DailyItem(

	@field:SerializedName("moonset")
	val moonset: Int ,

	@field:SerializedName("sunrise")
	val sunrise: Int ,

	@field:SerializedName("temp")
	val temp: Temp ,

	@field:SerializedName("moon_phase")
	val moonPhase:  Double ,

	@field:SerializedName("uvi")
	val uvi: Double ,

	@field:SerializedName("moonrise")
	val moonrise: Int ,

	@field:SerializedName("pressure")
	val pressure: Int,

	@field:SerializedName("clouds")
	val clouds: Int,

	@field:SerializedName("feels_like")
	val feelsLike: FeelsLike,

	@field:SerializedName("wind_gust")
	val windGust:  Double,

	@field:SerializedName("dt")
	val dt: Int ,

	@field:SerializedName("pop")
	val pop:  Double,

	@field:SerializedName("wind_deg")
	val windDeg: Int,

	@field:SerializedName("dew_point")
	val dewPoint:  Double,

	@field:SerializedName("sunset")
	val sunset: Int,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("wind_speed")
	val windSpeed:  Double,

	@field:SerializedName("rain")
	val rain:  Double
)

data class Current(

	@field:SerializedName("sunrise")
	val sunrise: Int,

	@field:SerializedName("temp")
	val temp: Double,

	@field:SerializedName("visibility")
	val visibility: Int,

	@field:SerializedName("uvi")
	val uvi: Double,

	@field:SerializedName("pressure")
	val pressure: Int,

	@field:SerializedName("clouds")
	val clouds: Int,

	@field:SerializedName("feels_like")
	val feelsLike:  Double,

	@field:SerializedName("wind_gust")
	val windGust:  Double,

	@field:SerializedName("dt")
	val dt: Int,

	@field:SerializedName("wind_deg")
	val windDeg: Int,

	@field:SerializedName("dew_point")
	val dewPoint: Double,

	@field:SerializedName("sunset")
	val sunset: Int,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("wind_speed")
	val windSpeed:  Double
)

data class Temp(

	@field:SerializedName("min")
	val min: Double,

	@field:SerializedName("max")
	val max: Double,

	@field:SerializedName("eve")
	val eve:  Double,

	@field:SerializedName("night")
	val night:  Double,

	@field:SerializedName("day")
	val day:  Double,

	@field:SerializedName("morn")
	val morn:  Double
)

data class AlertsItem(

	@field:SerializedName("start")
	val start: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("sender_name")
	val senderName: String,

	@field:SerializedName("end")
	val end: Int,

	@field:SerializedName("event")
	val event: String,

	/*@field:SerializedName("tags")
	val tags: List<String>*/
)



data class MinutelyItem(

	@field:SerializedName("dt")
	val dt: Int,

	@field:SerializedName("precipitation")
	val precipitation: Int
)