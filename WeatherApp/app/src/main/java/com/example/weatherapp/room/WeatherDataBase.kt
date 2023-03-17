package com.example.weatherapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.LocalDatabase.WeatherDAO
import com.example.weatherapp.model.*
import com.example.weatherapp.room.favourite.FavouriteDao


@Database(
    entities = [LocalModel::class, Favorite::class, Alert::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDAO(): WeatherDAO
    abstract fun favouriteDAO(): FavouriteDao
    //abstract fun favouriteWeatherDAO(): FavouriteDaooooo
    abstract fun alertDao(): AlertDao

    companion object {
        private var INSTANCE: WeatherDataBase? = null

        @Synchronized
        fun getInstance(context: Context): WeatherDataBase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDataBase::class.java,
                    "RoomWeatherTable"
                )

                    .fallbackToDestructiveMigration().build()
                INSTANCE = db
                db
            }
        }
    }
}

