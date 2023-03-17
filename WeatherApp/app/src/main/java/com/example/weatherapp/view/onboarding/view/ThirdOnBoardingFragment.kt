package com.example.weatherapp.view.onboarding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentThirdOnBoardingBinding

class ThirdOnBoardingFragment : Fragment() {
    private var _binding: FragmentThirdOnBoardingBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdOnBoardingBinding.inflate(inflater, container, false)

        //binding.nextTxt.setOnClickListener { SkipOnBoardingScreen() }

        binding.skipTxt.setOnClickListener{ SkipOnBoardingScreen() }

        binding.backTxt.setOnClickListener { BackOnBoardingScreen() }
        binding.progressBar.max = 100
        binding.progressBar.progress = 100
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun SkipOnBoardingScreen(){
  //      findNavController().navigate(R.id.action_thirdOnBoardingFragment_to_settingDialogFragment)
        Navigation.findNavController(requireView()).navigate(R.id.action_thirdOnBoardingFragment_to_settingDialogFragment)
    }
    private fun BackOnBoardingScreen(){
        findNavController().navigate(R.id.action_thirdOnBoardingFragment_to_seconedOnBoardingFragment)
    }

    }
