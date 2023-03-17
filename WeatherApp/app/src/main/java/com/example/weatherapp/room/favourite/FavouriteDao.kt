package com.example.weatherapp.room.favourite

import androidx.room.*
import com.example.weatherapp.model.Favorite
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteDao {
    @Query("SELECT * FROM FavouriteTable")
    fun getFavourite(): Flow<List<Favorite>>
    @Delete
    suspend fun deleteFavourite( favourie: Favorite)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(vararg favourite: Favorite)
}

