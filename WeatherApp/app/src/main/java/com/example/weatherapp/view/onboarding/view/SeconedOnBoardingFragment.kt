package com.example.weatherapp.view.onboarding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSeconedOnBoardingBinding

class SeconedOnBoardingFragment : Fragment() {
    private var _binding: FragmentSeconedOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeconedOnBoardingBinding.inflate(inflater, container, false)

        binding.nextTxt.setOnClickListener { NextOnBoardingScreen() }

        binding.skipTxt.setOnClickListener{ SkipOnBoardingScreen() }

        binding.backTxt.setOnClickListener { BackOnBoardingScreen() }
        binding.progressBar.max = 100
        binding.progressBar.progress = 74
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun NextOnBoardingScreen(){
        findNavController().navigate(R.id.action_seconedOnBoardingFragment_to_thirdOnBoardingFragment)
    }
    private fun BackOnBoardingScreen(){
        findNavController().navigate(R.id.action_seconedOnBoardingFragment_to_firstOnBoardingFragment)
    }
    private fun SkipOnBoardingScreen(){
        findNavController().navigate(R.id.action_seconedOnBoardingFragment_to_home)
    }


}
