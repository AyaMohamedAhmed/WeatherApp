package com.example.weatherapp.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface{
    suspend fun getAllResponseFromAPI(lat:Double,lon:Double,exclude:String) : Flow<Root>
    suspend fun insertUpdateLastVersion(localModel: LocalModel)
    suspend fun getLastVersionFromRoom() : Flow<LocalModel>

    suspend fun getFavourite(): Flow<List<Favorite>>
    suspend fun deleteFavourite( favourie: Favorite)
    suspend fun insertFavourite( favourite: Favorite)

    fun getAlert(): Flow<List<Alert>>
    suspend fun deleteAlert(alert: Alert)
    suspend fun insertAlert( alert: Alert)

    suspend fun getAllResponseFromAPIForWorker(lat:Double,lon:Double) : Root

   /* suspend fun insertFavouriteWeather(root: Root)
    suspend fun getFavouriteWeather(): Flow<List<Root>>*/
}