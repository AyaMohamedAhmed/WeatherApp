package com.example.weatherapp.view.favourite.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FavouriteItemBinding
import com.example.weatherapp.model.Favorite

class FavouriteAdapter(private val favouriteList: List<Favorite>,val listener: FavouriteOnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var binding: FavouriteItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = FavouriteItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val favouriteItem = favouriteList?.get(position)
        binding.addressFavourite.text = favouriteItem?.title +" "+favouriteItem?.address
            //Utils.getAddress(
//            context,
//            LatLng(favouriteItem!!.latitude, favouriteItem.longitude)
//        )?.locality.toString()
        binding.cancelFavourite.setOnClickListener {
            listener.deleteFavouriteLocation(favouriteItem!!)
        }
        binding.layout.setOnClickListener {
            listener.onClick(favouriteItem!!)
        }

    }

    override fun getItemCount(): Int = favouriteList?.size ?: 0


    class ViewHolder(var binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}