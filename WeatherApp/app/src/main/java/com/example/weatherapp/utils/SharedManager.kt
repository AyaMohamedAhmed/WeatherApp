package com.example.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.model.Settings
import com.google.gson.Gson

const val SETTINGS = "SETTINGS"
class SharedManager(var context:Context) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    var settings:Settings

    get(){
        sharedPreferences.getString(SETTINGS,"").also {
            if (it?.isEmpty() == true)
                return Settings()
            else
                return Gson().fromJson(it,Settings::class.java)
        }

    }
    set(value) {
        val editor = sharedPreferences.edit()
        val data = Gson().toJson(value)
        editor.putString(SETTINGS,data)
        editor.apply()
    }
}