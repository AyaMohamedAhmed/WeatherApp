package com.example.weatherapp.view.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Home.ViewModel.HomeViewModel
import com.example.weatherapp.Home.ViewModel.HomeViewModelFactory
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.LocalModel
import com.example.weatherapp.model.Repository
import com.example.weatherapp.utils.ApiState
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.utils.ConvertUnit
import com.example.weatherapp.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.*


private const val TAG = "HomeFragment"
private lateinit var binding: FragmentHomeBinding
private lateinit var homeViewModel: HomeViewModel
private lateinit var homeViewModelFactory: HomeViewModelFactory
private lateinit var repository: Repository
lateinit var sharedPreference: SharedPreferences
lateinit var editor: SharedPreferences.Editor

class HomeFragment : Fragment() {

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        val language = sharedPreference.getString(Constant.LANGUAGE,Constant.English )
        if(language=="ar")setLanguage(language)


        mFusedLocationProviderClient
            .getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ).addOnSuccessListener { location ->
                location?.let {
                    editor.putFloat(Constant.LAT, location.latitude.toFloat())
                    editor.putFloat(Constant.LON, location.longitude.toFloat())
                    editor.commit()
                }

            }
        /*when (language) {
            ("ar") -> setLanguage(language)
            else -> {
                setLanguage(language)

            }
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = Repository.getInstance(requireActivity().application)
        val sharedPreferences =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        val lat = sharedPreferences.getFloat(Constant.LAT, 30.62116f)
        val long = sharedPreferences.getFloat(Constant.LON, 32.26987f)
        val lang = sharedPreferences.getString(Constant.LANGUAGE, Constant.English)!!

        homeViewModelFactory = HomeViewModelFactory(repository, sharedPreferences)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        if(Utils.isOnline(requireContext())) {
            if (arguments != null) {
                val favorite: Favorite = arguments?.getSerializable("fav") as Favorite
                homeViewModel.getWeather(favorite.latitude, favorite.longitude, lang)

            } else {
                homeViewModel.getWeather(
                    lat?.toDouble() ?: 0.0, long?.toDouble() ?: 0.0, lang
                )
            }
        }
        else{
            Toast.makeText(context,"You Are In Offline Mode That's Old Data",Toast.LENGTH_LONG).show()
            homeViewModel.getLastResponseFromRoom()
        }
        lifecycleScope.launchWhenCreated {
            homeViewModel.data.collect {
                when (it) {
                    is ApiState.Success -> {
                        binding.lottieHome.visibility=View.GONE
                        binding.nestedView.visibility=View.VISIBLE

                        Log.i(TAG, "onCreateView:  ${it.data.timezone}")
                        homeViewModel.insertLastResponse(LocalModel(wether = it.data))

                        binding.daysRecyclerView.adapter =
                            HomeDaysAdapter(it.data.daily, it.data.timezone!!)
                        binding.daysRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.daysRecyclerView.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                                .apply { orientation = RecyclerView.VERTICAL }
                        }
                        binding.hoursRecyclerView.adapter =
                            HomeHoursAdapter(it.data.hourly, it.data.timezone)
                        binding.hoursRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.hoursRecyclerView.apply {
                            layoutManager = LinearLayoutManager(requireContext()).apply {
                                orientation = RecyclerView.HORIZONTAL
                            }

                        }

                        binding.humidityHomeTv.text = it.data.current!!.humidity.toString()
                        binding.cloudHomeTv.text = it.data.current.clouds.toString()
                        binding.ultraVioletHomeTv.text = it.data.current.uvi.toString()
                        binding.pressureHomeTv.text = it.data.current.pressure.toString()

                        binding.windHomeTv.text =
                            changeWindSpeed(it.data.current.windSpeed).toString()
                        binding.visibilityHomeTv.text = it.data.current.visibility.toString()

                        binding.weatherTypeHomeTv.text = it.data.current.weather[0]?.description

                        binding.windSpeedTv.text =
                            changeWindSpeed(it.data.current.windSpeed).toString()
                        binding.numberCelsuis.text =
                            changeWeatherData(it.data.current.temp).toString()

                        binding.locationHomeTv.text = Utils.getAddress(
                            requireContext(),
                            LatLng(it.data.lat!!, it.data.lon!!)
                        )?.getAddressLine(0).toString().split(",").get(0)
                        /*Glide.with(binding.iconHome)
                            .load(Utils.getWeatherIcon(it.data.current.weather[0]!!.icon))
                            .into(binding.iconHome)*/

                    }


                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        binding.lottieHome.visibility = View.VISIBLE
                        binding.nestedView.visibility=View.GONE
                    }
                }
            }
        }


    }

    fun changeWeatherData(temp: Double): Double {
        var tempratureType = 0.0
        var sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var tempUnit = sharedPreference.getString(Constant.TEMPRATUREUNIT, Constant.CeLSIUS)
        when (tempUnit) {
            (Constant.CeLSIUS) -> tempratureType = ConvertUnit.convertFromKelvinToCelsius(temp)
            (Constant.FAHRENHEIT) -> tempratureType = ConvertUnit.convertFromKelvinToFahrenheit(temp)
            else -> {
                tempratureType = temp
            }
        }
        return tempratureType
    }

    fun changeWindSpeed(temp: Double): Double {
        var windType = 0.0
        var sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var windUnit = sharedPreference.getString(Constant.WINDUNIT, Constant.MILEHOUR)
        when (windUnit) {
            (Constant.MILEHOUR) -> windType = ConvertUnit.convertMeterspersecToMilesperhour(temp)

            else -> {
                windType = temp
            }
        }
        return windType
    }

    private fun setLanguage(language: String) {
        val metric = resources.displayMetrics
        val configuration = resources.configuration
        configuration.locale = Locale(language)
        Locale.setDefault(Locale(language))

        configuration.setLayoutDirection(Locale(language))
        // update configuration
        resources.updateConfiguration(configuration, metric)
        // notify configuration
        onConfigurationChanged(configuration)

    }





}





