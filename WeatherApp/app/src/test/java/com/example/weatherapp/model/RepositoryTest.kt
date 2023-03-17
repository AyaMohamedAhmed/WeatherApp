package com.example.weatherapp.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.retrofit.FakeRemoteSource
import com.example.weatherapp.room.FakeLocalSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var  remoteDataSource : FakeRemoteSource
    private lateinit var localDataSource: FakeLocalSource
    private lateinit var repository: Repository

    var alert1=Alert(1,"12-3-2023","14-3-2023","10:00 AM",0.0,0.0)
    var alert2=Alert(2,"10-3-2023","12-3-2023","11:00 AM",0.0,0.0)
    var alert3=Alert(3,"1-3-2023","8-3-2023","12:00 AM",0.0,0.0)
    var alert4=Alert(4,"9-3-2023","10-3-2023","1:00 AM",0.0,0.0)
    private var alertList:MutableList<Alert> = mutableListOf(alert1,alert2)


    var favorite1=Favorite("f1","add1",0.0,0.0)
    var favorite2=Favorite("f2","add2",0.0,0.0)
    var favorite3=Favorite("f3","add3",0.0,0.0)
    var favorite4=Favorite("f4","add4",0.0,0.0)
    private var favList:MutableList<Favorite> = mutableListOf(favorite1,favorite2)

    var weather1=LocalModel(1,Root())
    var weather2=LocalModel(2, Root())
    var root1=Root(lat=0.0,lon=0.0)
    var root2=Root()


    @Before
    fun setUp() {
        remoteDataSource= FakeRemoteSource(root1)
        localDataSource= FakeLocalSource(favList,alertList,weather1)
        repository= Repository(localDataSource,remoteDataSource)
    }

    @Test
    fun getAllResponseFromAPI_returnTrueIfFounded()=mainCoroutineRule.runBlockingTest {
        //when test response from API
        val response=repository.getAllResponseFromAPI(lat = 0.0, lon = 0.0,"en").first()
        //then true if the same response founded
        assertThat(response,`is`(root1))
    }

    @Test
    fun insertUpdateLastVersion_insertLastUpdate()=mainCoroutineRule.runBlockingTest {
        //Given insert
        repository.insertUpdateLastVersion(weather1)
        //when
        val response=localDataSource.getLastVersionFromRoom()
        //then true if inserted
        assertThat(response.wether.lon,`is`(weather1.wether.lon))
    }

    @Test
    fun getLastVersionFromRoom_getLastSavedData()=mainCoroutineRule.runBlockingTest {
        //Given insert item
        repository.insertUpdateLastVersion(weather2)
        //when request item
        val response=repository.getLastVersionFromRoom().first()
        //then True
        assertThat(response.wether.lon,`is`(weather2.wether.lon))
    }


    @Test
    fun insertFavourite_insertFavouriteElement_True()=mainCoroutineRule.runBlockingTest{
        //when inset favourite element
        repository.insertFavourite(favorite3)
        //then check if inserted
        assertThat(favList.size,`is`(3))

    }

        @Test
    fun deleteAlert_deleteElement_True()=mainCoroutineRule.runBlockingTest {
        //when delete alert element
        repository.deleteAlert(alert1)
        //then check if deleted
        assertThat(alertList.size,`is`(1))

    }

   }