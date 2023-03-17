package com.example.weatherapp.view.home.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.HomeHoursBinding
import com.example.weatherapp.model.HourlyItem
import com.example.weatherapp.utils.Utils

class HomeHoursAdapter(val weatherHourlyList:List<HourlyItem>, val timeZone: String) :
    RecyclerView.Adapter<HomeHoursAdapter.ViewHolder>(){

    private lateinit var binding: HomeHoursBinding

    class ViewHolder(var binding: HomeHoursBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHoursAdapter.ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
        binding =HomeHoursBinding.inflate(inflater, parent, false)
        return HomeHoursAdapter.ViewHolder(binding)
    }


    override fun getItemCount(): Int= weatherHourlyList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HomeHoursAdapter.ViewHolder, position: Int) {
        var hour: HourlyItem = weatherHourlyList[position]
        holder.binding.nameHourItem.text=Utils.getTime("hh a",hour.dt.toLong())
        holder.binding.weatherHourItem.text=hour.weather[0]?.description
        Glide.with(holder.binding.iconHourItem).load(Utils.getWeatherIcon(hour.weather[0]!!.icon)).into(holder.binding.iconHourItem)



    }

}