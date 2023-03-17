package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteTable")
data class Favorite(

    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "address")
    var address:String = "",
    @ColumnInfo(name = "latitude")
    var latitude:Double,
     @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "longitude")
    var longitude:Double,

):java.io.Serializable
