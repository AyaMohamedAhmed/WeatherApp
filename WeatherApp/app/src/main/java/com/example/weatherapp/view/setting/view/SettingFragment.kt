package com.example.weatherapp.view.setting.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSettingBinding
import com.example.weatherapp.model.Settings
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.utils.Utils
import java.util.*

private const val TAG = "SettingFragment"
class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var strLanguage: String = ""
    private var strTempUnit: String = ""
    private var strWindUnit: String = ""
    private var strLocation: String = ""
    private lateinit var setting: Settings


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //location
        if (sharedPreference.getString(Constant.LOCATION, Constant.GPS) == "GPS") {
            binding.gpsRadioBtn.isChecked = true
        } else {
            binding.MapRadioBtn.isChecked = true
        }

        //temp unit
        if (sharedPreference.getString(
                Constant.TEMPRATUREUNIT,
                Constant.CeLSIUS
            ) == Constant.CeLSIUS
        ) {
            binding.celsiusRadioBtn.isChecked = true
        } else if (sharedPreference.getString(
                Constant.TEMPRATUREUNIT,
                Constant.CeLSIUS
            ) == Constant.FAHRENHEIT
        ) {
            binding.fahrenhitRadioBtn.isChecked = true
        } else if (sharedPreference.getString(
                Constant.TEMPRATUREUNIT,
                Constant.CeLSIUS
            ) == Constant.KELVIN
        ) {
            binding.kelvinRadioBtn.isChecked = true
        }

        //wind unit
        if (sharedPreference.getString(Constant.WINDUNIT, Constant.MILEHOUR) == Constant.MILEHOUR) {
            binding.WindMileRadioBtn.isChecked = true
        } else {
            binding.WindMeterRadioBtn.isChecked = true
        }
        //alarm

        if (sharedPreference.getString(Constant.NOTIFICATIONSGROUP,Constant.NOTIFICATION) == Constant.NOTIFICATION
        ) {
            Log.i(TAG, "onViewCreated: notifitcation setti"+sharedPreference.getString(
                Constant.NOTIFICATIONSGROUP,
                Constant.NOTIFICATION
            ))
            binding.notificationRadioBtn.isChecked = true
        } else {
            Log.i(TAG, "onViewCreated: alarm setting "+sharedPreference.getString(
                Constant.NOTIFICATIONSGROUP,
                Constant.ALARM
            ))
            binding.AlarmRadioBtn.isChecked = true
        }
        //lang
        if (sharedPreference.getString(Constant.LANGUAGE, Constant.English) == Constant.English) {
            binding.englishRadioBtn.isChecked = true
        } else {
            binding.arabicRadioBtn.isChecked = true
        }




        binding.LocationGroup.setOnCheckedChangeListener { radioGroup, id ->
            val radioTempUnit: RadioButton = view.findViewById(id)
            strLocation = radioTempUnit.text.toString()
            editor.putString(Constant.LOCATION, strLocation)
            editor.apply()
            if (strLocation == Constant.MAP) {
                if (Utils.isOnline(requireContext())) {
                    findNavController().navigate(R.id.action_setting_to_mapSettingFragment)
                }
                else{
                    Toast.makeText(context,"You Are In Offline Mode Plz Turn On Network",Toast.LENGTH_LONG).show()

                }
            }
        }

        binding.languageGroup.setOnCheckedChangeListener { _, _ ->
            if (binding.arabicRadioBtn.isChecked) {
                strLanguage = Constant.Arabic
            } else {
                strLanguage = Constant.English
            }
            editor.putString(Constant.LANGUAGE, strLanguage)
            editor.apply()
            setLanguage(strLanguage, requireContext())
        }

        binding.TemperatureGroup.setOnCheckedChangeListener { radioGroup, id ->
            val radioTempUnit: RadioButton = view.findViewById(id)
            strTempUnit = radioTempUnit.text.toString()
            editor.putString(Constant.TEMPRATUREUNIT, strTempUnit)
            editor.commit()
        }

        binding.WindSpeedGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioWindUnit: RadioButton = view.findViewById(i)
            strWindUnit = radioWindUnit.text.toString()
            editor.putString(Constant.WINDUNIT, strWindUnit)
            editor.apply()
        }

        binding.NotificationGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioNotification: RadioButton = view.findViewById(i)
            editor.putString(Constant.NOTIFICATIONSGROUP, radioNotification.text.toString())
            editor.commit()
        }

    }

    private fun setLanguage(language: String, context: Context) {
        val metric = resources.displayMetrics
        val configuration = resources.configuration
        configuration.locale = Locale(language)
        Locale.setDefault(Locale(language))
        configuration.setLayoutDirection(Locale(language))
        configuration.setLocale(Locale(language))
        // update configuration
        resources.updateConfiguration(configuration, metric)
        // notify configuration
        onConfigurationChanged(configuration)
        startActivity(Intent(context, context::class.java))


    }


}