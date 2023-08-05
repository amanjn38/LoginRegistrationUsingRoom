package com.example.loginregistrationusingroom.ui.screens

import android.content.Context
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginregistrationusingroom.R
import android.os.Handler
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("RememberLogin", Context.MODE_PRIVATE)
        val rememberMe = sharedPreferences.getBoolean("remember_me", false)

        if (rememberMe) {
            findNavController().navigate(R.id.action_splashFragment_to_logoutFragment4)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                // Use findNavController().navigate() to navigate to FragmentB
                findNavController().navigate(R.id.action_splashFragment_to_onBoardFragment)
            }, 1500)
        }


    }
}