package com.example.weatherapp.LocalDatabase

import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.LocalModel
import kotlinx.coroutines.flow.Flow


interface LocalSource {
    suspend fun insertUpdateLastVersion(localModel: LocalModel)
    suspend fun getLastVersionFromRoom(): LocalModel


    /*  suspend fun insertFavouriteWeather(favouriteModel: FavouriteModel)
      suspend fun getFavouriteWeather() : Flow<List<FavouriteModel>>*/

    fun getFavourite(): Flow<List<Favorite>>
    suspend fun deleteFavourite(favourie: Favorite)
    suspend fun insertFavourite(favourite: Favorite)

    fun getAlert(): Flow<List<Alert>>
    suspend fun deleteAlert(alert: Alert)
    suspend fun insertAlert( alert: Alert)

}
