package com.example.weatherapp.view.favourite.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.model.Favorite
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

class FavouriteFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var geoCoder:Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
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


        autocompleteFragment.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onError(status: Status) {
                Log.i("Erorr palace", "An error occurred: $status")
            }

            override fun onPlaceSelected(palce: Place) {
                mMap.addMarker(
                    MarkerOptions()
                        .position(palce.latLng)
                        .title(palce.name))

                /*
                world : 1
                contitnet: 5
                cites : 10
                streets:15
                 */
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
                    val fav = Favorite(
                        data.countryName,
                        data.adminArea,
                        data.latitude,
                        data.longitude
                    )
                    showDialog(fav);
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
//        Utils.updateMyLocation(requireActivity()){
//            val sydney = LatLng(it.latitude, it.longitude)
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(sydney)
//                    .title("Marker in Sydney"))
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//
//        }

        mMap.setOnMarkerClickListener { marker ->
            val address =geoCoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)

            address?.let {
                if (it.isNotEmpty()){
                    var data = it[0]
                    val fav = Favorite(
                        data.countryName,
                        data.adminArea,
                        data.latitude,
                        data.longitude
                    )
                    showDialog(fav);
                }

            }

            true
        }


//        mMap.setOnPoiClickListener {
//            val fav = Favorite(
//                it.name,
//                "",
//                it.latLng.latitude,
//                it.latLng.longitude
//            )
//
//            showDialog(fav)
//        }
    }

    private fun showDialog(favorite: Favorite) {
        val alertBuild:AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuild.setTitle("Save To Favorite")
        alertBuild.setMessage("DO YOU WANT TO SAVE THIS CITY TO YOUR FAVORITE?")
        alertBuild.setPositiveButton("SAVE") { _, _ ->
           // TODO save data in room
            Toast.makeText(requireContext(), "$favorite", Toast.LENGTH_SHORT).show()

        }

        alertBuild.setNegativeButton("NOPE!") { _, _ ->

        }

        val alert = alertBuild.create()
        alert.show()


    }


}