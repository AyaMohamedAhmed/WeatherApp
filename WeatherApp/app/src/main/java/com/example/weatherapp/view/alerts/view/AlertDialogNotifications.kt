package com.example.weatherapp.view.alerts.view

import android.content.Context
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAlertDialogNotificationsBinding


class AlertDialogNotifications(private val context: Context, private val description: String){
    lateinit var binding: FragmentAlertDialogNotificationsBinding
    private lateinit var dialog: View
    private var sound: MediaPlayer = MediaPlayer.create(context, R.raw.rainsound)

    fun onCreate() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialog = inflater.inflate(R.layout.fragment_alert_dialog_notifications, null)
        binding = FragmentAlertDialogNotificationsBinding.bind(dialog)
        binding.descriptionMsg.text = description
        binding.dismissBtn.setOnClickListener {
        try {
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(dialog)
                dialog.invalidate()
                (dialog.parent as ViewGroup).removeAllViews()
            } catch (e: Exception) {
            }
            sound.release()
        }
        val LAYOUT_FLAG: Int = Flag()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams: WindowManager.LayoutParams = Params(LAYOUT_FLAG)
        windowManager.addView(dialog, layoutParams)
        sound.start()

    }

    private fun Flag(): Int {
        val LAYOUT_FLAG: Int
        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }
        else {WindowManager.LayoutParams.TYPE_PHONE}
        return LAYOUT_FLAG
    }

    private fun Params(LAYOUT_FLAG: Int): WindowManager.LayoutParams {
        val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
        return WindowManager.LayoutParams(
            width,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT
        )
    }


}