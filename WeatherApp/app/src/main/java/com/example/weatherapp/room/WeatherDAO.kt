package com.example.weatherapp.LocalDatabase
import androidx.room.*

import com.example.weatherapp.model.LocalModel


@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateLastVersion(localModel: LocalModel)

    @Query("SELECT * FROM RoomWeatherTable")
    suspend fun getLastVersionFromRoom() : LocalModel




}