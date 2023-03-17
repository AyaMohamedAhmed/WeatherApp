package com.example.weatherapp.retrofit

import com.example.weatherapp.model.Root

interface RemoteSource  {
    suspend fun getWeatherData(
         latitude: Double,
         longitude: Double,
        language: String="en",
         exclude: String = EXCLUDE,
         apiKey: String = API_KEY
    ): Root

}