package com.example.weatherapp.Home.ViewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.LocalModel
import com.example.weatherapp.model.Repository
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(val repository: Repository,val sharedPreferences: SharedPreferences): ViewModel() {

    private var _data = MutableStateFlow<ApiState>(ApiState.Loading)
    var data = _data


    fun getWeather(lat:Double, long:Double, lang:String) = viewModelScope.launch {

        repository.getAllResponseFromAPI(lon = long, lat = lat, language = lang).catch { e ->
            _data.value = ApiState.Failure(e)
        }.collect { data ->
            Log.i("AYA", "getAllWeatherStander: ${data.timezone}")
            _data.value = ApiState.Success(data)
        }
    }


    fun insertLastResponse(localModel: LocalModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUpdateLastVersion(localModel)
        }
    }


    fun getLastResponseFromRoom() =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getLastVersionFromRoom().catch { e ->
                    _data.value = ApiState.Failure(e)
                }.collect { data ->
                    _data.value = ApiState.Success(data.wether)
                }

            }
        }


}
