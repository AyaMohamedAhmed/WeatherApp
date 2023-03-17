package com.example.weatherapp.view.favourite.viewmodel

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
class FavouriteViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    var alert1=Alert(1,"12-3-2023","14-3-2023","10:00 AM",0.0,0.0)
    var alert2=Alert(2,"10-3-2023","12-3-2023","11:00 AM",0.0,0.0)
    var alert3=Alert(3,"1-3-2023","8-3-2023","12:00 AM",0.0,0.0)
    var alert4=Alert(4,"9-3-2023","10-3-2023","1:00 AM",0.0,0.0)
    private var alertList:MutableList<Alert> = mutableListOf(alert1,alert2)


    var favorite1= Favorite("f1","add1",0.0,0.0)
    var favorite2= Favorite("f2","add2",0.0,0.0)
    var favorite3= Favorite("f3","add3",0.0,0.0)
    var favorite4= Favorite("f4","add4",0.0,0.0)
    private var favouriteList:MutableList<Favorite> = mutableListOf(favorite1,favorite2)

    var weather1= LocalModel(1, Root())
    var root1= Root(lat=0.0,lon=0.0)

    private lateinit var repository: RepositoryInterface
    private lateinit var favouriteViewModel: FavouriteViewModel



    @Test
    fun insertFavorite_inserElement_True() = runTest {  //Given
    repository=FakeRepository(favouriteList,alertList,weather1,root1)
    favouriteViewModel= FavouriteViewModel(repository)

    //when inserted to db
    repository.insertFavourite(favorite3)

    //then check if inserted
    assertThat(favouriteList.size,`is`(3))
}
    @Test
    fun deleteFavourite() = runTest {  //Given
        repository=FakeRepository(favouriteList,alertList,weather1,root1)
        favouriteViewModel=FavouriteViewModel(repository)
        //when removed from db
        repository.insertFavourite(favorite3)
        repository.deleteFavourite(favorite3)

        //then check if deleted
        assertThat(favouriteList.size,`is`(2))
    }

}