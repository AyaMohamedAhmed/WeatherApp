package com.example.weatherapp.view.alerts.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapp.R
import com.example.weatherapp.model.AlertsItem
import com.google.gson.Gson

const val CHANNEL_ID = "channelID"
private val audioPlayer: MediaPlayer = MediaPlayer()
lateinit var alertsItem:AlertsItem

class MyBroadCastReciever() : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {


        if (!intent.getStringExtra("key").isNullOrEmpty())
        {
            alertsItem= Gson().fromJson(intent.getStringExtra("key"),AlertsItem::class.java)

        }

        Log.i("TAGggggggggggggggggggggggggggggggggggggggggggggggg", "onReceive:${alertsItem.description} ")
        var sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var notificationUnit = sharedPreference.getString("notification", "Alarm")
        when (notificationUnit) {
            ("Alarm") -> {
                setAlarm(context, "Weather App", "Your Alarm !!!!!!!")
                audioPlayer.start()

                    /*val dialogBuild: AlertDialog.Builder = AlertDialog.Builder(context.applicationContext)
                    dialogBuild.setIcon(R.drawable.icon)
                    dialogBuild.setTitle("Weather App")
                    dialogBuild.setMessage("your Alarm !!!!!!!!!!!!!!!!!!!!!!!")
                    dialogBuild.setCancelable(false)
                    dialogBuild.setPositiveButton("Ok") { dialogInterface: DialogInterface, i: Int ->}
                    val dialog = dialogBuild.create()
                    dialog.show()
*/

            }

            else -> {
                setNotification(context, "Weather App", "Your Notification !!!!!!!")
            }
        }


    }
}

private fun setAlarm(context: Context, title: String, description: String) {
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

}

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











