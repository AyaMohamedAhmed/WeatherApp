package com.example.weatherapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

const val PERMISSION_ID=11

class MainActivity : AppCompatActivity() {
    var drawerLayout: DrawerLayout? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home, R.id.setting, R.id.favourite,R.id.alerts
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        getMyLocation(this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout!!.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_ID){
            if(grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                showLocationPermissionDialog()
            }else{
                if(!isLocationEnabled(this)){
                    Toast.makeText(this,"plz turn on location", Toast.LENGTH_SHORT).show()
                    val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }
        }
    }


    companion object{
        fun getMyLocation(activity: Activity){
            if(checkPermissions(activity)){
                if(!isLocationEnabled(activity)){
                    Toast.makeText(activity,"plz turn on location", Toast.LENGTH_SHORT).show()
                    val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activity.startActivity(intent)
                }
            }else{
                requestPermissions(activity)

            }
        }



        private fun isLocationEnabled(activity:Activity):Boolean{
            val locationManager: LocationManager =activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        private fun checkPermissions(activity:Activity):Boolean{
            return ActivityCompat.checkSelfPermission(activity.applicationContext,android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity.applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        }

        private fun requestPermissions(activity:Activity){
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID)
        }


    }





    fun showLocationPermissionDialog(){
        val dialogBuild:AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuild.setIcon(R.drawable.icon)
        dialogBuild.setTitle("Weather App")
        dialogBuild.setMessage("Please Allow Weather App Access your location")
        dialogBuild.setCancelable(false)
        dialogBuild.setPositiveButton("Allow"){ dialogInterface: DialogInterface, i: Int ->
            getMyLocation(this)
        }

        val dialog = dialogBuild.create()
        dialog.show()
    }


}