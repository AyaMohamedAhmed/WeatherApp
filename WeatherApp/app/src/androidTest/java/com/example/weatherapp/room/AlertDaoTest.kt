package com.example.weatherapp.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.model.Alert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class AlertDAOTest {

    private lateinit var db: WeatherDataBase
    private lateinit var alertDao: AlertDao
    val alarm=Alert(1,"10-10-2020","1-1-2022","10:00 AM",0.0,0.0)
    val alarm2=Alert(2,"17-5-2022","14-8-2023","12:00 AM",0.0,0.0)
    val alarm3=Alert(3,"1-5-2021","14-8-2022","1:00 AM",0.0,0.0)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @After
    fun tearDown() {
     db.close()
    }

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        alertDao=db.alertDao()
    }

    @Test
    fun getAlert_UpdateRoom_True()= runBlocking {
        alertDao.insertAlert(alarm)
        alertDao.insertAlert(alarm2)
        //when get alarm from db
        val response=alertDao.getAlert().first()
        //then
        assertThat(response.size,`is`(2))

    }

    @Test
    fun deleteAlert_deleteAlertItem_True()= runBlocking {
        //given delete Item
        alertDao.insertAlert(alarm)
        alertDao.insertAlert(alarm2)
        alertDao.deleteAlert(alarm)
        //when data deleted
        val responsee=alertDao.getAlert().first()
        //then return true
        assertThat(responsee.get(0).longitude,`is`(alarm2.longitude))


    }

    @Test
    fun insertAlert_insertAletItem_TrueInserted()= runBlocking {
    //given insert Item
        alertDao.insertAlert(alarm)
    //when data inserted
        val responsee=alertDao.getAlert().first()
    //then return true
        assertThat(responsee.get(0).longitude,`is`(alarm.longitude))




    }
}