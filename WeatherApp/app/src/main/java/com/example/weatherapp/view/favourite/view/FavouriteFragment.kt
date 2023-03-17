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
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Repository
import com.example.weatherapp.view.favourite.viewmodel.FavouriteViewModel
import com.example.weatherapp.view.favourite.viewmodel.FavouriteViewModelFactory
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
   private lateinit var factory: FavouriteViewModelFactory
   private lateinit var repository: Repository
    private lateinit var favouriteViewModel: FavouriteViewModel

   /* private val favouriteViewModel: favouriteViewModel by lazy{
        ViewModelProvider(this,favouriteViewModelFactory(Repository(LocalSourceImpl(requireContext())))).get(favouriteViewModel::class.java)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val localSource=LocalSourceImpl(requireContext())
        val apiClient= APIClient.getInstane()
        repository=Repository(localSource,apiClient)*/
        repository = Repository.getInstance(requireActivity().application)
        factory=FavouriteViewModelFactory(repository)
        favouriteViewModel=ViewModelProvider(this,factory).get(FavouriteViewModel::class.java)
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

              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(palce.latLng,10f))
               //favouriteViewModel.saveWeather(palce.latLng)
            }
        })
        //todo here if crash
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


    }

    private fun showDialog(favorite: Favorite) {
        val alertBuild:AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuild.setTitle("Save To Favorite")
        alertBuild.setMessage("DO YOU WANT TO SAVE THIS CITY TO YOUR FAVORITE?")
        alertBuild.setPositiveButton("SAVE") { _, _ ->
            favouriteViewModel.insertFavorite(favorite)
            Toast.makeText(requireContext(), "$favorite", Toast.LENGTH_SHORT).show()

        }

        alertBuild.setNegativeButton("NOPE!") { _, _ ->

        }

        val alert = alertBuild.create()
        alert.show()


    }


}