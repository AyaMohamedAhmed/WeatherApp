package com.example.weatherapp.view.alerts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.RepositoryInterface
import com.example.weatherapp.utils.AlertsApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlertsViewModel(repositoryInterface: RepositoryInterface) : ViewModel() {
    private var _data = MutableStateFlow<AlertsApiState>(AlertsApiState.alertLoading)
    var data = _data
    private val repository: RepositoryInterface = repositoryInterface

    fun getAlert() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.getAlert().catch { e ->
                _data.value = AlertsApiState.alertFailure(e)
            }.collect { data ->
                _data.value = AlertsApiState.alertSuccess(data)
            }
        }
    }

    fun insertAlert(alert: Alert) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAlert(alert)
        }
    }

    fun deleteAlert(alert: Alert) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAlert(alert)
        }
    }


}