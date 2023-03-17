package com.example.weatherapp.room

import androidx.room.*
import com.example.weatherapp.model.Alert
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM AlertTable")
    fun getAlert(): Flow<List<Alert>>
    @Delete
    suspend fun deleteAlert( alert: Alert)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(vararg alert: Alert)
}