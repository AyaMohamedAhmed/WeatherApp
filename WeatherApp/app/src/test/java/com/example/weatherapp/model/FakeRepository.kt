package com.example.weatherapp.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository(

    private var favouriteList: MutableList<Favorite> = mutableListOf<Favorite>(),
    private var alertList: MutableList<Alert> = mutableListOf<Alert>(),
    private var rootRoom: LocalModel = LocalModel(0, wether = Root()),
    private var root: Root = Root()


) : RepositoryInterface {
    override suspend fun getAllResponseFromAPI(
        lat: Double,
        lon: Double,
        exclude: String
    ): Flow<Root> {
        TODO("Not yet implemented")
    }

    override suspend fun insertUpdateLastVersion(localModel: LocalModel) {
        rootRoom = localModel
    }

    override suspend fun getLastVersionFromRoom(): Flow<LocalModel> = flow {
        emit(rootRoom)
    }

    override suspend fun getFavourite(): Flow<List<Favorite>> = flow {
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

    override suspend fun getAllResponseFromAPIForWorker(lat: Double, lon: Double): Root {
        return root
    }

}