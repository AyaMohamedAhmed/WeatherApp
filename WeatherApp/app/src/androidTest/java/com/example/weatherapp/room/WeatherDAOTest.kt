package com.example.weatherapp.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.LocalDatabase.WeatherDAO
import com.example.weatherapp.model.LocalModel
import com.example.weatherapp.model.Root
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDAOTest {
    private lateinit var db: WeatherDataBase
    private lateinit var weatherDAO: WeatherDAO
    val weather=LocalModel(1, Root())
    val weather2=LocalModel(2, Root())
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        weatherDAO=db.weatherDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertUpdateLastVersion_insertWeather_returnTrue()=runBlocking {
        //given insert element in db
        weatherDAO.insertUpdateLastVersion(weather)
        weatherDAO.insertUpdateLastVersion(weather2)
        //when get response
        val response=weatherDAO.getLastVersionFromRoom()
        //then true
        assertThat(response.wether.lon,`is`(weather.wether.lon))

    }

    @Test
    fun getLastVersionFromRoom_checkThatLastElementReturned()=runBlocking {
        //given insert element in db
        weatherDAO.insertUpdateLastVersion(weather)
        weatherDAO.insertUpdateLastVersion(weather2)
        //when get response
        val response=weatherDAO.getLastVersionFromRoom()
        //then true
        assertThat(response.wether.lon,`is`(weather2.wether.lon))

    }
}