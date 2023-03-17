package com.example.weatherapp.view.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

   // lateinit var sharedManager: SharedManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //sharedManager = SharedManager(requireContext())
        val sharedPreferences =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        val open = sharedPreferences.getBoolean("open",false)
    lifecycleScope.launch(Dispatchers.Main) {
    delay(3000)

       // if (sharedManager.settings.isFirst != true) {
        if (open){
            this.cancel()
            findNavController().navigate(R.id.action_splashFragment_to_home)
        }
        else {
            this.cancel()
            findNavController().navigate(R.id.action_splashFragment_to_firstOnBoardingFragment)
        }
    }
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

}