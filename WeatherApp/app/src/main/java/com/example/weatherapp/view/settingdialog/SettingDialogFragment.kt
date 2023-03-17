package com.example.weatherapp.view.settingdialog

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.utils.Constant

class SettingDialogFragment : Fragment() {
    lateinit var location: RadioButton
    lateinit var locationGroup: RadioGroup
    lateinit var notification: RadioButton
    lateinit var notificationGroup: RadioGroup
    //lateinit var sharedManager: SharedManager
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        // sharedManager = SharedManager(requireContext())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationGroup = view.findViewById(R.id.location_radio_group)
        notificationGroup = view.findViewById(R.id.Notification_Radio_Group)

        locationGroup.setOnCheckedChangeListener { group, checkedId ->
            location = view.findViewById(checkedId)
            when (location.text.toString()) {
               Constant.MAP -> {
                    /*val settingsData = sharedManager.settings
                    settingsData.locationType = "Map"
                    settingsData.isFirst = false
                    sharedManager.settings = settingsData*/
                    editor.putString(Constant.LOCATION,Constant.MAP)
                    editor.apply()

                    findNavController().navigate(R.id.action_settingDialogFragment_to_mapSettingFragment)

                }
               Constant.GPS-> {
                    /*val settingsData = sharedManager.settings
                    settingsData.locationType = "GPS"
                    settingsData.isFirst = false
                    sharedManager.settings = settingsData*/

                    editor.putString(Constant.LOCATION,Constant.GPS)
                    editor.apply()
                    findNavController().navigate(R.id.action_settingDialogFragment_to_home)

                }
            }

            notification = view.findViewById(checkedId)
            notificationGroup.setOnCheckedChangeListener { group, checkedId ->

                when (notification.text.toString()) {
                    Constant.NOTIFICATION -> {
                        /* sharedManager.settings = sharedManager.settings.apply {
                             notificationType = "Notification"
                             isFirst = false*/
                        Log.i("TAG", "onViewCreated: notification "+notification.text)
                        editor.putString(Constant.NOTIFICATIONSGROUP, Constant.NOTIFICATION)
                        editor.commit()
                    }

//                    getString(R.string.Alarm) -> {
                    Constant.ALARM-> {
                        /*sharedManager.settings = sharedManager.settings.apply {
                            notificationType = "Alarm"
                            isFirst = false*/
                        Log.i("TAG", "onViewCreated: alarm"+notification.text)
                        editor.putString(Constant.NOTIFICATIONSGROUP, Constant.ALARM)

                        editor.commit()
                    }
                }


            }


        }
    }
}

