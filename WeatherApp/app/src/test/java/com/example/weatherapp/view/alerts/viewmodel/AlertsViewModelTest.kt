package com.example.weatherapp.view.alerts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlertsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    var alert1=Alert(1,"12-3-2023","14-3-2023","10:00 AM",0.0,0.0)
    var alert2=Alert(2,"10-3-2023","12-3-2023","11:00 AM",0.0,0.0)
    var alert3=Alert(3,"1-3-2023","8-3-2023","12:00 AM",0.0,0.0)
    private var alertList:MutableList<Alert> = mutableListOf(alert1,alert2)


    var favorite1= Favorite("f1","add1",0.0,0.0)
    var favorite2= Favorite("f2","add2",0.0,0.0)
    private var favList:MutableList<Favorite> = mutableListOf(favorite1,favorite2)

    var weather1= LocalModel(1, Root())
    var root1= Root(lat=0.0,lon=0.0)

    private lateinit var repository: RepositoryInterface
    private lateinit var alertsViewModel: AlertsViewModel


    @Test
    fun insertAlert_isInserted_true()=runTest {
        //Given
        repository=FakeRepository(favList,alertList,weather1,root1)
        alertsViewModel=AlertsViewModel(repository)
        alertsViewModel.insertAlert(alert1)
        alertsViewModel.insertAlert(alert2)
        alertsViewModel.insertAlert(alert3)


        //then check if inserted
        assertThat(alertList.size,`is`(5))
    }

    @Test
    fun deleteAlert_deleteElement_True()= runTest {
        //Given
        repository=FakeRepository(favList,alertList,weather1,root1)
        alertsViewModel=AlertsViewModel(repository)
        alertsViewModel.insertAlert(alert1)
        alertsViewModel.insertAlert(alert2)
        alertsViewModel.insertAlert(alert3)

        //when delete alert element
        alertsViewModel.deleteAlert(alert1)

        //then check if deleted
        assertThat(alertList.size,`is`(5))
    }
}