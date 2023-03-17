package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RoomWeatherTable")
data class LocalModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id:Int?=null,
    @ColumnInfo(name = "data")
    val wether: Root


)
