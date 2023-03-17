package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "AlertTable")
data class Alert(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int?=null,
    @ColumnInfo(name = "startDate")
    var startDate:String,
    @ColumnInfo(name = "endDate")
    var endDate:String = "",
    @ColumnInfo(name = "time")
    var time:String,
    @ColumnInfo(name = "latitude")
    var latitude:Double,
    @ColumnInfo(name = "longitude")
    var longitude:Double


    ):java.io.Serializable