package com.example.weatherapp.retrofit

import com.example.weatherapp.model.Root
import retrofit2.http.GET
import retrofit2.http.Query

const val EXCLUDE = "minutely"
const val API_KEY = "8bdc89e28e3ae5c674e20f1d16e70f7d"

interface APIinterface {
    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String="en",
        @Query("exclude") exclude: String = EXCLUDE,
        @Query("appid") apiKey: String = API_KEY
    ): Root


    
}

