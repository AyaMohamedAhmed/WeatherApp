package com.example.weatherapp.Home.ViewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repository

class HomeViewModelFactory(val repository: Repository,val sharedPreferences: SharedPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository,sharedPreferences) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel not founded")
        }


    }
}