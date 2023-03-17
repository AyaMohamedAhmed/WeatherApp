package com.example.weatherapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "FavouriteWeathers")
data class FavouriteModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id:Int?=null,
    @ColumnInfo(name = "data")
    val wether: String


)

@Dao
interface FavouriteDaooooo {
    @Query("SELECT * FROM FavouriteWeathers")
    fun getFavourite(): Flow<List<FavouriteModel>>

    @Delete
    suspend fun deleteFavourite( favouriteModel: FavouriteModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(vararg favouriteModel: FavouriteModel)
}