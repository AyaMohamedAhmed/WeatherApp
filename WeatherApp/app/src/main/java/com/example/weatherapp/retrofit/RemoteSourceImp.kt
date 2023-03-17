package com.example.weatherapp.retrofit

import com.example.weatherapp.model.Root

class RemoteSourceImp(var apiInterface: APIinterface) : RemoteSource {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        language: String,
        exclude: String,
        apiKey: String
    ): Root {
        return apiInterface.getWeatherData(latitude, longitude, language, exclude, apiKey)
    }
}