package com.example.weatherapp.view.onboarding.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFirstOnBoardingBinding

class FirstOnBoardingFragment : Fragment() {
    private var _binding:FragmentFirstOnBoardingBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding=FragmentFirstOnBoardingBinding.inflate(inflater,container,false)
       // val sharedPreferences:SharedPreferences=requireContext().getSharedPreferences("sharedpreference.txt", Context.MODE_PRIVATE)
        val sharedPreferences = requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        val editor=sharedPreferences.edit()
        editor.putBoolean("open",true)
        editor.apply()
        binding.nextTxt.setOnClickListener { NextOnBoardingScreen() }
        binding.skipTxt.setOnClickListener { SkipOnBoardingScreen() }
        binding.progressBar.max=100
        binding.progressBar.progress=30
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun NextOnBoardingScreen(){
        findNavController().navigate(R.id.action_firstOnBoardingFragment_to_seconedOnBoardingFragment)
    }
    private fun SkipOnBoardingScreen(){
        findNavController().navigate(R.id.action_firstOnBoardingFragment_to_settingDialogFragment)
    }

}
