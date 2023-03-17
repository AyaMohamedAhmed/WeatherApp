package com.example.weatherapp.model

import androidx.room.TypeConverter
import com.google.gson.Gson

object Converters {
    @TypeConverter
    fun fromStringToData(stringData: String)= Gson().fromJson(stringData, Root::class.java)
    @TypeConverter
    fun fromDataToString(rootData: Root)=Gson().toJson(rootData)
    @TypeConverter
    fun fromListToList(listFavourite:List<FavouriteModel>):List<Root>{
       var favouriteRootList:MutableList<Root> = mutableListOf()
        repeat(listFavourite.size){
            favouriteRootList.add(fromStringToData(listFavourite.get(it).wether))
        }
        return favouriteRootList

    }

}