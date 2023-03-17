package com.example.weatherapp.view.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherapp.utils.FavouriteApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteViewModel(repositoryInterface: RepositoryInterface): ViewModel() {
    private var _data = MutableStateFlow<FavouriteApiState>(FavouriteApiState.favLoading)
    var data = _data
    private val repository: RepositoryInterface = repositoryInterface

    fun getFavourite() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.getFavourite().catch { e ->
                _data.value = FavouriteApiState.FavFailure(e)
            }.collect { data ->
                _data.value = FavouriteApiState.FavSuccess(data)
            }
        }
    }

    /*fun saveWeather(latLng: LatLng){
        viewModelScope.launch {
            repository.getAllResponseFromAPI(latLng.latitude,latLng.longitude,"minutely").collect{
                    repository.insertFavouriteWeather(it)
            }


        }
    }*/
    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavourite(favorite)
        }
    }
    fun deleteFavourite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavourite(favorite)
        }
    }


}




