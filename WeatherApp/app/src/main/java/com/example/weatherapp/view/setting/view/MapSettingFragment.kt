package com.example.weatherapp.view.setting.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.model.currentLocation
import com.example.weatherapp.utils.Constant
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MapSettingFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var geoCoder: Geocoder
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor=sharedPreference.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geoCoder  = Geocoder(requireActivity())
        var apiKey = resources.getString(R.string.api_key)
        // Initialize the SDK
        Places.initialize(requireContext(), apiKey)

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(requireContext())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.i("Erorr palace", "An error occurred: $status")
            }

            override fun onPlaceSelected(palce: Place) {
                mMap.addMarker(
                    MarkerOptions()
                        .position(palce.latLng)
                        .title(palce.name))

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(palce.latLng,10f))            }
        })
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener {
            val address =geoCoder.getFromLocation(it.latitude, it.longitude, 1)

            address?.let {
                if (it.isNotEmpty()){
                    var data = it[0]
                    val currentLocation = currentLocation(
                        data.latitude,
                        data.longitude
                    )
                    showDialog(currentLocation);
                }

            }


        }


        mMap.isMyLocationEnabled  =true

        mMap.setOnMapClickListener {
            mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("THis")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it,10f))
        }


        mMap.setOnMarkerClickListener { marker ->
            val address =geoCoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)

            address?.let {
                if (it.isNotEmpty()){
                    var data = it[0]
                    val currentLocation = currentLocation(
                         data.latitude,
                        data.longitude
                    )
                    showDialog(currentLocation);
                }

            }

            true
        }


    }

    private fun showDialog(currentLocation: currentLocation) {
        val alertBuild: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuild.setTitle("Current Location")
        alertBuild.setMessage("Do you want to save your location?")
        alertBuild.setPositiveButton("SAVE") { _, _ ->

            editor.putFloat(Constant.LAT, currentLocation.langitude?.toFloat() ?: 0f)
            editor.putFloat(Constant.LON,currentLocation.longitude?.toFloat() ?: 0f)
            editor.apply()
            Log.i("ayaaaaaaaaaaaaaaaaa", "showDialog: ${currentLocation.langitude}")
            findNavController().navigate(R.id.action_mapSettingFragment_to_home)
        }

        alertBuild.setNegativeButton("NOPE!") { _, _ ->


        }

        val alert = alertBuild.create()
        alert.show()


    }




}