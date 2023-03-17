package com.example.weatherapp.retrofit

import com.example.weatherapp.model.Root

class FakeRemoteSource(private var root: Root) : RemoteSource {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        language: String,
        exclude: String,
        apiKey: String
    ): Root {
        return root
    }


}