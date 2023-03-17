package com.example.weatherapp.view.home.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.HomeDayBinding
import com.example.weatherapp.model.DailyItem
import com.example.weatherapp.utils.Utils

class HomeDaysAdapter(val weatherDailyList:List<DailyItem>,val timeZone: String)
    : RecyclerView.Adapter<HomeDaysAdapter.ViewHolder>(){

    private lateinit var binding: HomeDayBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
        binding = HomeDayBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int=weatherDailyList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var day:DailyItem = weatherDailyList[position]
        holder.binding.nameDayItem.text=Utils.getDay(day.dt,timeZone)
        holder.binding.weather.text=day.weather[0]?.description
       Glide.with(holder.binding.iconWeatherDayItem).load(Utils.getWeatherIcon(day.weather[0]!!.icon)).into(holder.binding.iconWeatherDayItem)



    }

    class ViewHolder(var binding: HomeDayBinding) : RecyclerView.ViewHolder(binding.root)
}