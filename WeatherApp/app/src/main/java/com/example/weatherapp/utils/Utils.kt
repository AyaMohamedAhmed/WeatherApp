package com.example.weatherapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class Utils {
    companion object {
        @SuppressLint("MissingPermission")
        fun updateMyLocation(activity: Activity, getMyLocation: (location: Location) -> Unit) {
            var mFusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(activity)
            val mLocationRequest = LocationRequest()
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            mLocationRequest.setInterval(0)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                object : LocationCallback() {

                    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                    override fun onLocationResult(locationResult: LocationResult) {
                        val mLastLocation: Location? = locationResult.getLastLocation()
                        getMyLocation(mLastLocation!!)

                    }

                },
                Looper.myLooper()
            )

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDay(dt: Int, timezone: String, format: String = "EEE"): String {
            val zoneId = ZoneId.of(timezone)
            val instant = Instant.ofEpochSecond(dt.toLong())
            val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
            return instant.atZone(zoneId).format(formatter)
        }

        fun getWeatherIcon(weatherIcon: String): Int {

            when (weatherIcon) {
                "01d", "01n" ->return R.drawable.clear_sky
                "02d", "02n" -> return R.drawable.few_cloud
                "03d", "03n" -> return R.drawable.scatterd_cloud
                "04d", "04n" -> return R.drawable.broken_cloud
                "09d", "09n" -> return R.drawable.shower_rain
                "10d", "10n" -> return R.drawable.rain
                "11d", "11n" -> return R.drawable.thunder_storm
                "13d", "13n" -> return R.drawable.snow
                "5d","5n"->return R.drawable.mist
                else->return R.drawable.icon            }

        }


        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        fun getAddress(context: Context,latLng: LatLng)=Geocoder(context).
        getFromLocation(latLng.latitude, latLng.longitude, 1)?.get(0)

        fun getTime(pattern:String,date:Long)=SimpleDateFormat(pattern,Locale.getDefault()).format(Date(date*1000))

        fun getAddresslongLat(context: Context,lat:Double, lon:Double)
        = Geocoder(context).getFromLocation(lat,lon,5)?.get(0)
    }
}