package com.example.weatherapp.view.alerts.view

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDialogAlertBinding
import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.Repository
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.view.alerts.viewmodel.AlertsViewModel
import com.example.weatherapp.view.alerts.viewmodel.AlertsViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AlertDialogFragment : Fragment() {
    private var myTime: Long = 0
    // var alert: AlertsItem = AlertsItem(1, "aya", "aya", 0, "0")
    lateinit var binding: FragmentDialogAlertBinding
    private val currentDate: Calendar = Calendar.getInstance()
    private var year = currentDate[Calendar.YEAR]
    private var month = currentDate[Calendar.MONTH]
    private var day = currentDate[Calendar.DAY_OF_MONTH]
    private var firstday: Int? = null
    private var lastday: Int? = null
    private var hour: Int? = null
    private var minute: Int? = null
    private var myEndDate: Long = 0
    private lateinit var alertsViewModel: AlertsViewModel
    private lateinit var alertsViewModelFactory: AlertsViewModelFactory
    private lateinit var repository: Repository
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences
    private var startDate: String = ""
    private var endDate: String = ""
    private var time: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val localSource = LocalSourceImpl(requireContext())
        //val apiClient = APIClient.getInstane()
        sharedPreferences =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        //repository = Repository(localSource, apiClient)
        repository = Repository.getInstance(requireActivity().application)
        mFusedLocationProviderClient = FusedLocationProviderClient(requireActivity())
        alertsViewModelFactory = AlertsViewModelFactory(repository)
        alertsViewModel =
            ViewModelProvider(this, alertsViewModelFactory).get(AlertsViewModel::class.java)

        binding.startDateEditTxt.setOnClickListener {
            selectStartDate()
        }
        binding.endDateEditTxt.setOnClickListener {
            selectEndDate()
        }
        binding.selectTimeEditTxt.setOnClickListener {
            clickTimePicker(view)

        }

        binding.saveBtnAlertDialog.setOnClickListener {
            val lat = sharedPreferences.getFloat(Constant.LAT, 0f)
            val long = sharedPreferences.getFloat(Constant.LON, 0f)

            alertsViewModel.insertAlert(
                Alert(
                    startDate = startDate,
                    endDate = endDate,
                    time = time,
                    latitude = lat.toDouble(),
                    longitude = long.toDouble()
                )
            )
            if(timeCheker( startDate,endDate)){

                setAlarmWorker()
                findNavController().navigate(R.id.action_alertDialogFragment_to_alerts)
            }
            else{
                alertsViewModel.deleteAlert(Alert(
                    startDate = startDate,
                    endDate = endDate,
                    time = time,
                    latitude = lat.toDouble(),
                    longitude = long.toDouble()
                ))
            }

        }
        //hour?.let {
        //setAlarm(it, minute ?: 0)
        //}

    }


    private fun selectStartDate() {
        val mDatePicker = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                day = selectedDay
                firstday = day
                month = selectedMonth
                year = selectedYear
                currentDate.set(year, month, day)

                startDate = sdf.format(currentDate.time)
                binding.startDateEditTxt.text = sdf.format(currentDate.time)
            }, year, month, day
        )

        mDatePicker.show()
    }

    private fun selectEndDate() {
        val mDatePicker = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                day = selectedDay
                lastday = day
                month = selectedMonth
                year = selectedYear
                currentDate.set(year, month, day)
                myEndDate = currentDate.timeInMillis
                endDate = sdf.format(currentDate.time)
                binding.endDateEditTxt.text = sdf.format(currentDate.time)
            }, year, month, day
        )

        mDatePicker.show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickTimePicker(view: View) {

        TimePickerDialog(
            requireContext(), { _, h, m ->
                hour = h
                minute = m
                binding.selectTimeEditTxt.text = "$h:$m "
                time = "$h:$m "
                myTime =
                    (TimeUnit.MINUTES.toSeconds(m.toLong()) + TimeUnit.HOURS.toSeconds(h.toLong()))
                myTime = myTime.minus(3600L * 2)
            }, 12, 0, false
        ).show()


    }




    private fun setAlarmWorker() {
        val date = Calendar.getInstance().timeInMillis.div(1000)
        val inQueue = ((date - myTime) / 60 / 60 / 60 / 60) - 115
        val data = Data.Builder()
        data.putLong("endDate", myEndDate)
        val worker = PeriodicWorkRequestBuilder<com.example.weatherapp.view.alerts.view.Worker>(
            1,
            TimeUnit.DAYS
        )
            .setInitialDelay(inQueue, TimeUnit.SECONDS)
            .addTag("$inQueue")
            .setInputData(data.build())
            .build()
        WorkManager.getInstance(requireContext()).enqueue(worker)

    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun timeCheker(startDate:String,endDate:String): Boolean {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val dayNow = getDateMillis("$day/${month + 1}/$year")
        val startDate=getDateMillis("${startDate}")
        val endDate=getDateMillis("${endDate}")
        return dayNow >= startDate && dayNow <= endDate
    }


    private fun getDateMillis(date: String): Long {
        val f = SimpleDateFormat("dd/MM/yyyy")
        val d: Date = f.parse(date)
        return d.time
    }

}


