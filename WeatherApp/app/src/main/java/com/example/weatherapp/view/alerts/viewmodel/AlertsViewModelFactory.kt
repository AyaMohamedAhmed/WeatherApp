package com.example.weatherapp.view.alerts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.Repository

class AlertsViewModelFactory(val repository: Repository) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AlertsViewModel::class.java)) {
                AlertsViewModel(repository) as T
            } else {
                throw java.lang.IllegalArgumentException("ViewModel not founded")
            }


        }
    }