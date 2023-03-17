package com.example.weatherapp.retrofit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class APIinterfaceTest {
    private var APIInterface: APIinterface?=null

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        APIInterface= APIClient.getInstane()
    }


    @Test
    fun getWeatherData_gitLanAndLon_True() = runBlocking {
        //Given latitude and longitude
        var lat =61.52401
        var long=105.3187561

        //When found response checked
        val response=APIInterface?.getWeatherData(latitude = lat, longitude = long)

        //Then return true
        assertThat(response,IsNull.notNullValue())

    }
}