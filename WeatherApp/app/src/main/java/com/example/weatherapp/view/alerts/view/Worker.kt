package com.example.weatherapp.view.alerts.view

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapp.R
import com.example.weatherapp.model.Repository
import com.example.weatherapp.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class Worker(var context: Context, var paras: WorkerParameters) : CoroutineWorker(context, paras) {
    private lateinit var repository: Repository
    private var lat: Double = 0.0
    private var long: Double = 0.0

    override suspend fun doWork(): Result {
        val endDate = inputData.getLong("endDate", 0)
        val date = Calendar.getInstance().timeInMillis

        if (date > endDate) {
            val sharedPreference =
                context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            lat = sharedPreference.getFloat("lat", 0f).toDouble()
            long = sharedPreference.getFloat("lon", 0f).toDouble()
            repository = Repository.getInstance(context as Application)
            val response = repository.getAllResponseFromAPIForWorker(lat, long)
            var alertDescription: String=""
            if(response.alerts.isEmpty()) {
               alertDescription= "Weather is fine,no alerts for the schedule time period \n The weather is: ${
                    response.current?.weather?.get(0)?.description ?: ""
                }"
            }
            else{
                 alertDescription= response.alerts?.get(0)?.description?:""
            }


            when (sharedPreference.getString(Constant.NOTIFICATIONSGROUP, Constant.ALARM)) {
                ("Alarm") -> {
                    //setAlarm(context, "Weather App", alertDescription)
                    GlobalScope.launch(Dispatchers.Main) {
                        AlertDialogNotifications(context, alertDescription).onCreate()
                    }
                }
                else -> {
                    setNotification(context, "Weather App", alertDescription)
                }
            }
            return Result.success()
        } else {
            return Result.failure()
            // WorkManager.getInstance(context).cancelAllWorkByTag("")

        }

    }

    /*private fun setAlarm(context: Context, title: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(title)
            .setContentText(description)
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }

    }*/
    private fun setNotification(context: Context, title: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }

    }
}










