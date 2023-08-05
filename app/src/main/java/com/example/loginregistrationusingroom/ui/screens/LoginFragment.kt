package com.example.loginregistrationusingroom.ui.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loginregistrationusingroom.R
import com.example.loginregistrationusingroom.databinding.FragmentLoginBinding
import com.example.loginregistrationusingroom.ui.viewmodels.LoginViewModel
import com.example.loginregistrationusingroom.utilities.IS_LOGGED_IN
import com.example.loginregistrationusingroom.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDontHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_onBoardFragment)
        }

        binding.btLogin.setOnClickListener {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            if (validateInputs(name, password)) {

                loginViewModel.login(name, password)
            }
        }

        loginViewModel.loginStatusLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Handle successful login
                    // Navigate to the logout screen or perform any other action
                    findNavController().navigate(R.id.action_loginFragment_to_logoutFragment)
                    Toast.makeText(
                        requireContext(),
                        resource.message ?: "Logged in successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    IS_LOGGED_IN = true
                    if (binding.cbRememberMe.isChecked) {
                        val sharedPreferences =
                            requireContext().getSharedPreferences(
                                "RememberLogin",
                                Context.MODE_PRIVATE
                            )
                        sharedPreferences.edit().putBoolean("remember_me", true).apply()
                    }
                    loginViewModel.clearLoginData()
                }
                is Resource.Error -> {
                    // Handle login error
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
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_loginFragment_to_onBoardFragment)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun validateInputs(name: String, password: String): Boolean {
        return name.isNotBlank() && password.isNotBlank()
    }
}