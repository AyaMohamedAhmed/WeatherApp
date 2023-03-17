package com.example.weatherapp.model

data class Settings(
    var isFirst:Boolean = true,
    var unitTemperature:String = "",
    var language:String = "",
    var locationLat:Double = 0.0,
    var locationLong:Double = 0.0,
    var windSpeed:String ="",
    var locationType:String = "",
    var notificationType:String = ""
    ) {

}