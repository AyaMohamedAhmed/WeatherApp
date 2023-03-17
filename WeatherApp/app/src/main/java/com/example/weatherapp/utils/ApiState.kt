package com.example.weatherapp.utils

import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Root


sealed interface  ApiState {
    class Success(val data: Root) : ApiState
    class Failure(val msg: Throwable) : ApiState
    object Loading : ApiState
}


sealed interface FavouriteApiState{
    class FavSuccess(val data: List<Favorite>):FavouriteApiState
    class FavFailure(val msg: Throwable):FavouriteApiState
    object favLoading:FavouriteApiState
}


sealed interface AlertsApiState{
    class alertSuccess(val data: List<Alert>):AlertsApiState
    class alertFailure(val msg: Throwable):AlertsApiState
    object alertLoading:AlertsApiState
}
