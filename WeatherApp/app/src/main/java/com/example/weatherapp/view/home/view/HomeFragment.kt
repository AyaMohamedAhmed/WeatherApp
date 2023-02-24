package com.example.weatherapp.view.home.view

import android.annotation.SuppressLint

import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.google.android.gms.location.*


class HomeFragment : Fragment() {


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())



        val mLocationRequest= LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)


        mFusedLocationClient=LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, object : LocationCallback(){
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun onLocationResult(locationResult: LocationResult?) {
                val mLastLocation: Location = locationResult!!.getLastLocation()

                val geoCoder = Geocoder(requireContext())
                val address =geoCoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1)

            }

        }, Looper.myLooper())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onResume() {
        super.onResume()
    }





    }



