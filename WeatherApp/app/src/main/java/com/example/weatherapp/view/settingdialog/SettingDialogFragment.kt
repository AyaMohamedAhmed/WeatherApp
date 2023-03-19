package com.example.weatherapp.view.settingdialog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.utils.SharedManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class SettingDialogFragment : Fragment() {
    lateinit var location: RadioButton
    private lateinit var locationGroup: RadioGroup
    private lateinit var notificationGroup: RadioGroup
    private lateinit var notification: RadioButton
    private lateinit var sharedManager: SharedManager
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor



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

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationGroup = view.findViewById(R.id.location_radio_group)
        notificationGroup = view.findViewById(R.id.Notification_Radio_Group)
        notificationGroup.setOnCheckedChangeListener { _, check ->
            when (check) {
                R.id.notification_radio_button-> {
                    editor.putString(Constant.NOTIFICATIONSGROUP, Constant.NOTIFICATION)
                    editor.apply()
                }

                R.id.Alarm_radio_button-> {
                    editor.putString(Constant.NOTIFICATIONSGROUP, Constant.ALARM)
                    editor.apply()
                }
            }
        }

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
            /* sharedManager.settings = sharedManager.settings.apply {
                             notificationType = "Notification"
                             isFirst = false*/

            /*sharedManager.settings = sharedManager.settings.apply {
                            notificationType = "Alarm"
                            isFirst = false*/
        }

    }
}

