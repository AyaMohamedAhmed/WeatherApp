package com.example.weatherapp.view.favourite.view

import com.example.weatherapp.model.Favorite

interface FavouriteOnClickListener {
    fun deleteFavouriteLocation(favorite: Favorite)

    fun onClick(favorite: Favorite)
}