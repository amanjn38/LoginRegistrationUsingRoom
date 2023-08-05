package com.example.loginregistrationusingroom.ui.screens

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.loginregistrationusingroom.R
import com.example.loginregistrationusingroom.databinding.FragmentLogoutBinding
import com.example.loginregistrationusingroom.utilities.IS_LOGGED_IN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : Fragment() {
    private var _binding: FragmentLogoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (IS_LOGGED_IN) {
                    showExitConfirmationDialog()
                }

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun showExitConfirmationDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                requireActivity().finish()
            }
            .setNegativeButton("No", null)
            .create()

        dialog.show()
    }

    private fun showLogoutConfirmationDialog() {
        val sharedPreferences =
            requireContext().getSharedPreferences("RememberLogin", Context.MODE_PRIVATE)

        // Create an AlertDialog.Builder instance
        val builder = AlertDialog.Builder(requireContext())

        // Set the dialog title and message
        builder.setTitle("Logout")
            .setMessage("Are you sure you want to logout?")

        // Set the positive button and its click listener
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            // Clear the shared preference
            if (sharedPreferences.contains("remember_me")) {
                sharedPreferences.edit().remove("remember_me").apply()
            }
            IS_LOGGED_IN = false

            findNavController().navigate(R.id.action_logoutFragment_to_loginFragment)
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
        }

        // Set the negative button and its click listener
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
            // Dismiss the dialog if the user chooses not to logout
            dialogInterface.dismiss()
        }

        // Create and show the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }

}