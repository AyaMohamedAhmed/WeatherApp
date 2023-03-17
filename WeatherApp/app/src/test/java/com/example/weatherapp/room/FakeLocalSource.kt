package com.example.weatherapp.room

import com.example.weatherapp.LocalDatabase.LocalSource
import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.LocalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalSource(
    private var favouriteList: MutableList<Favorite> = mutableListOf<Favorite>(),
    private var alertList: MutableList<Alert> = mutableListOf<Alert>(),
    private var root: LocalModel
) : LocalSource {
    override suspend fun insertUpdateLastVersion(localModel: LocalModel) {
        root = localModel
    }

    override suspend fun getLastVersionFromRoom(): LocalModel {
        return root
    }

    override fun getFavourite(): Flow<List<Favorite>> = flow {
        emit(favouriteList)
    }

    override suspend fun deleteFavourite(favourie: Favorite) {
        favouriteList.remove(favourie)
    }

    override suspend fun insertFavourite(favourite: Favorite) {
        favouriteList.add(favourite)
    }

    override fun getAlert(): Flow<List<Alert>> = flow {
        emit(alertList)
    }

    override suspend fun deleteAlert(alert: Alert) {
        alertList.remove(alert)
    }

    override suspend fun insertAlert(alert: Alert) {
        alertList.add(alert)
    }


}