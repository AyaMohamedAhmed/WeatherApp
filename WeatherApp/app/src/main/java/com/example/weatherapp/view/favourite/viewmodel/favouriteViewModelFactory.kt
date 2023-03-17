package com.example.weatherapp.view.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repository


class FavouriteViewModelFactory(val repository: Repository) :
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            FavouriteViewModel(repository) as T
        }
        else{
            throw java.lang.IllegalArgumentException("ViewModel not founded")
        }


    }
}