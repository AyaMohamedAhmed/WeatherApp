package com.example.weatherapp.view.alerts.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.AlarmItemBinding
import com.example.weatherapp.model.Alert


class AlertAdapter(val alarmList:List<Alert>,val listener: AlertOnClickListener): RecyclerView.Adapter<AlertAdapter.ViewHolder>(){

    private lateinit var binding: AlarmItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
        binding = AlarmItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int=alarmList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var alertsItem: Alert = alarmList[position]
        holder.binding.dateAlarm.text=alertsItem.startDate.toString()
        holder.binding.TimeAlarm.text=alertsItem.time.toString()
        holder.binding.cancelAlarm.setOnClickListener {
            listener.deleteAlert(alertsItem)

        }

    }

    class ViewHolder(var binding: AlarmItemBinding) : RecyclerView.ViewHolder(binding.root)
}


