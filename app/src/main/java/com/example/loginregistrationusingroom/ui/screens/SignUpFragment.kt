package com.example.loginregistrationusingroom.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginregistrationusingroom.R
import com.example.loginregistrationusingroom.databinding.FragmentSignUpBinding
import com.example.loginregistrationusingroom.ui.viewmodels.SignUpViewModel
import com.example.loginregistrationusingroom.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = signUpViewModel.getCountries()
        // Create an ArrayAdapter using the country names list and a default spinner layout
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        binding.countrySpinner.adapter = adapter

        // Set a listener to handle spinner item selection
        binding.countrySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Get the selected country name
                    // Do something with the selected country, like displaying it in a TextView
                    // or performing any other action you want.
                    // For example, you can update a TextView with the selected country name:
                    // updateTextView(selectedCountry)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case when no item is selected
                }
            }

        binding.btSignup.setOnClickListener() {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val selectedCountry = binding.countrySpinner.selectedItem as String
            if (validateInputs(name, password, selectedCountry)) {
                signUpViewModel.registerUser(name, password, selectedCountry)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill all the fields",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.tvBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvAlreadyHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        signUpViewModel.registrationStatusLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Handle successful registration
                    // Navigate to the login screen or perform any other action
                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    Toast.makeText(
                        requireContext(),
                        "Registered Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    signUpViewModel.clearSignUpData()
                }
                is Resource.Error -> {
                    // Handle registration error
                    // Show appropriate error message to the user
                    Toast.makeText(
                        requireContext(),
                        resource.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.etName.text.clear()
                    binding.etPassword.text.clear()
                }
            }
        }
    }

    private fun validateInputs(name: String, password: String, selectedCountry: String?): Boolean {
        // Validate the name
        if (name.isBlank()) {
            Toast.makeText(
                requireContext(),
                "Please enter the name",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        // Validate the password length
        if (password.length < 8) {
            Toast.makeText(
                requireContext(),
                "The minimum length of the password should be 8",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        if (selectedCountry!!.isBlank()) {
            return false
        }
        // Check for at least one lowercase letter, one uppercase letter, one number, and one special character
        var hasLowerCase = false
        var hasUpperCase = false
        var hasNumber = false
        var hasSpecialChar = false

        val specialCharacters = listOf('!', '@', '#', '$', '%', '&', '(', ')')

        for (char in password) {
            if (char.isLowerCase()) {
                hasLowerCase = true
            } else if (char.isUpperCase()) {
                hasUpperCase = true
            } else if (char.isDigit()) {
                hasNumber = true
            } else if (char in specialCharacters) {
                hasSpecialChar = true
            }
        }

        // Check if all conditions are met
        if (!hasLowerCase || !hasUpperCase || !hasNumber || !hasSpecialChar) {
            Toast.makeText(
                requireContext(),
                "The password should include atleast one number, special characters[!@#\$%&()], one lowercase letter, and one uppercase letter",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

}