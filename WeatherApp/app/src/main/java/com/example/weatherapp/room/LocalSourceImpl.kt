package com.example.weatherapp.room

import com.example.weatherapp.LocalDatabase.LocalSource
import com.example.weatherapp.LocalDatabase.WeatherDAO
import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.LocalModel
import com.example.weatherapp.room.favourite.FavouriteDao
import kotlinx.coroutines.flow.Flow

class LocalSourceImpl(
    private val weatherDAO: WeatherDAO?,
    //private val favouriteDaoo : FavouriteDaooooo?
    private val favouriteDao: FavouriteDao?,
    private val alertDao: AlertDao?

) : LocalSource {


    override suspend fun insertUpdateLastVersion(localModel: LocalModel) {
        weatherDAO?.insertUpdateLastVersion(localModel)
    }

    override suspend fun getLastVersionFromRoom(): LocalModel {
        return weatherDAO!!.getLastVersionFromRoom()
    }

    /*override suspend fun insertFavouriteWeather(favouriteModel: FavouriteModel) {
       favouriteDaoo?.insertFavourite(favouriteModel)


    }

    override suspend fun getFavouriteWeather(): Flow<List<FavouriteModel>> {
        return favouriteDaoo!!.getFavourite()
    }*/

    override fun getFavourite(): Flow<List<Favorite>> {
        return favouriteDao!!.getFavourite()
    }

    override suspend fun deleteFavourite(favourie: Favorite) {
        favouriteDao?.deleteFavourite(favourie)

    }

    override suspend fun insertFavourite(favourite: Favorite) {
        favouriteDao?.insertFavourite(favourite)
    }

    override fun getAlert(): Flow<List<Alert>> {
        return alertDao!!.getAlert()
    }

    override suspend fun deleteAlert(alert: Alert) {
        alertDao?.deleteAlert(alert)
    }

    override suspend fun insertAlert(alert: Alert) {
        alertDao?.insertAlert(alert)
    }


}