package com.example.weatherapp.model

import android.app.Application
import com.example.weatherapp.LocalDatabase.LocalSource
import com.example.weatherapp.retrofit.APIClient
import com.example.weatherapp.retrofit.RemoteSource
import com.example.weatherapp.retrofit.RemoteSourceImp
import com.example.weatherapp.room.LocalSourceImpl
import com.example.weatherapp.room.WeatherDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(var localSource: LocalSource, var remoteSource: RemoteSource) :
    RepositoryInterface {

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            app: Application
        ): Repository {
                return instance ?: synchronized(this) {

                    val apiInterface = APIClient.getInstane()
                    val remoteSource = RemoteSourceImp(apiInterface)
                    val db = WeatherDataBase.getInstance(app)
                    val favouriteDAO = db.favouriteDAO()
                    val weatherDAO = db.weatherDAO()
                    val alertsDAO = db.alertDao()
                    val localSource = LocalSourceImpl(weatherDAO, favouriteDAO, alertsDAO)
                    Repository(localSource, remoteSource)
                }

        }
    }

    override suspend fun getAllResponseFromAPI(
        lat: Double,
        lon: Double,
        language: String
    ): Flow<Root> = flow {
        emit(
            remoteSource.getWeatherData(
                latitude = lat,
                longitude = lon,
                language = language
            )
        )


    }

    override suspend fun insertUpdateLastVersion(localModel: LocalModel) {
        localSource.insertUpdateLastVersion(localModel)
    }

    override suspend fun getLastVersionFromRoom(): Flow<LocalModel> =
        flow {
            emit(localSource.getLastVersionFromRoom())
        }

    override suspend fun getFavourite(): Flow<List<Favorite>> {
        return localSource.getFavourite()

    }

    override suspend fun deleteFavourite(favourie: Favorite) {
        return localSource.deleteFavourite(favourie)
    }

    override suspend fun insertFavourite(favourite: Favorite) {
        return localSource.insertFavourite(favourite)
    }

    override fun getAlert(): Flow<List<Alert>> {
        return localSource.getAlert()
    }

    override suspend fun deleteAlert(alert: Alert) {
        return localSource.deleteAlert(alert)
    }

    override suspend fun insertAlert(alert: Alert) {
        return localSource.insertAlert(alert)
    }

    override suspend fun getAllResponseFromAPIForWorker(lat: Double, lon: Double): Root =
        remoteSource.getWeatherData(
            latitude = lat,
            longitude = lon
        )
}


/* override suspend fun insertFavouriteWeather(root: Root){
     localSource.insertFavouriteWeather(FavouriteModel(root.lat.toInt(),Converters.fromDataToString(root)?:""))
 }

 override suspend fun getFavouriteWeather()= flow{
     localSource.getFavouriteWeather().collect{
         this.emit(Converters.fromListToList(it))
     }*/








