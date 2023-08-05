package com.example.loginregistrationusingroom.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregistrationusingroom.data.models.user.User
import com.example.loginregistrationusingroom.data.repository.CountryRepository
import com.example.loginregistrationusingroom.data.repository.UserRepository
import com.example.loginregistrationusingroom.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository,
    private val context: Application
) : ViewModel() {
    // MutableLiveData to hold the registration status result.
    private val _registrationStatusLiveData = MutableLiveData<Resource<User>>()

    // LiveData exposed to observe the registration status result.
    val registrationStatusLiveData: LiveData<Resource<User>>
        get() = _registrationStatusLiveData

    // Function to get countries from the api
    fun getCountries() = countryRepository.readCountriesFromAssets(context)

    // Function to perform registration .
    fun registerUser(name: String, password: String, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Perform password validation here

                val user = User(name = name, password = password, country = country)
                userRepository.registerUser(user)
                _registrationStatusLiveData.postValue(Resource.Success(user))
            } catch (e: Exception) {
                _registrationStatusLiveData.postValue(Resource.Error("Registration failed"))
            }
        }
    }

    // Function to clear registration data, usually called when resetting the registration process.
    fun clearSignUpData() {
        _registrationStatusLiveData.value = Resource.Loading(null)
    }
}
