package com.example.weatherapp.utils

class ConvertUnit {

companion object {
    fun convertFromKelvinToCelsius(tempKelvin: Double): Double {
        val tempCelsius = Math.round(tempKelvin - 273.15f).toDouble()
        return tempCelsius

    }

    fun convertFromKelvinToFahrenheit(tempKelvin: Double): Double {
        val tempFahrenheit =Math.round( 1.8 * (tempKelvin - 273) + 32).toDouble()
        return tempFahrenheit

    }


    fun convertMeterspersecToMilesperhour(metersPerSec: Double): Double {
        val milesPerHour = Math.round(metersPerSec * 2.23694).toDouble()
        return milesPerHour
    }
}
}