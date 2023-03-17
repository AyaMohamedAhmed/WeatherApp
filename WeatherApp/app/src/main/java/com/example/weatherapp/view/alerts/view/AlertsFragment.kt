package com.example.weatherapp.view.alerts.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAlertsBinding
import com.example.weatherapp.model.Alert
import com.example.weatherapp.model.Repository
import com.example.weatherapp.utils.AlertsApiState
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.view.alerts.viewmodel.AlertsViewModel
import com.example.weatherapp.view.alerts.viewmodel.AlertsViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AlertsFragment : Fragment(), AlertOnClickListener {
    lateinit var binding: FragmentAlertsBinding
    private lateinit var alertsViewModel: AlertsViewModel
    private lateinit var alertsViewModelFactory: AlertsViewModelFactory
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOverlayPermission()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        repository = Repository.getInstance(requireActivity().application)
        alertsViewModelFactory = AlertsViewModelFactory(repository)
        alertsViewModel =
            ViewModelProvider(this, alertsViewModelFactory).get(AlertsViewModel::class.java)
        alertsViewModel.getAlert()
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Utils.isOnline(requireContext())) {
            binding.btnAlertAdd.setOnClickListener {

                findNavController().navigate(R.id.action_alerts_to_alertDialogFragment)
            }

            lifecycleScope.launchWhenCreated {
                alertsViewModel.data.collect {

                    when (it) {
                        is AlertsApiState.alertSuccess -> {
                            binding.alertRecycleView.adapter =
                                AlertAdapter(it.data, this@AlertsFragment)
                            binding.alertRecycleView.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.alertRecycleView.apply {
                                layoutManager = LinearLayoutManager(requireContext()).apply {
                                    orientation = RecyclerView.VERTICAL
                                }
                            }
                        }

                        is AlertsApiState.alertFailure -> {
                            Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                        }
                    }
                }
            }
        }
        else{
            binding.imageView11.visibility=View.VISIBLE
            binding.alertRecycleView.visibility=View.GONE
            binding.btnAlertAdd.visibility=View.GONE
        }
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(requireContext())) {
                val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                alertDialogBuilder.setTitle("weatherApp")
                    .setMessage("PLZ ALLOW WEATHER APP TO TAKE PERMISSION ")
                    .setPositiveButton("ok") { dialog: DialogInterface, i: Int ->
                        var myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                        startActivity(myIntent)
                        dialog.dismiss()
                    }.setNegativeButton(
                        "cancel"
                    ) { dialog: DialogInterface, i: Int ->
                        dialog.dismiss()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    }.show()
            }
        }
    }

    override fun deleteAlert(alert: Alert) {
        val alertBuild: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuild.setTitle("Delete Alarm")
        alertBuild.setMessage("DO YOU WANT TO DELETE THIS ALARM?")
        alertBuild.setPositiveButton("Delete") { _, _ ->
            alertsViewModel.deleteAlert(alert)
        }

        alertBuild.setNegativeButton("NOPE!") { _, _ ->

        }

        val alert = alertBuild.create()
        alert.show()


    }

}