package com.example.weatherapp.retrofit

import android.annotation.SuppressLint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "https://api.openweathermap.org/data/2.5/"

object APIClient {
    @SuppressLint("SuspiciousIndentation")
    fun getInstane(): APIinterface {
        val retrofitInstnce = Retrofit
            .Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitInstnce.create(APIinterface::class.java)

    }
}



