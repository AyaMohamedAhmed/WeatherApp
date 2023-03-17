package com.example.weatherapp.view.alerts.view

import com.example.weatherapp.model.Alert

interface AlertOnClickListener {
    fun deleteAlert(alert: Alert)
}