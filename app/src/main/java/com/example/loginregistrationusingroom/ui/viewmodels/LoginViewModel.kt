package com.example.loginregistrationusingroom.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregistrationusingroom.data.models.user.User
import com.example.loginregistrationusingroom.data.repository.UserRepository
import com.example.loginregistrationusingroom.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _loginStatusLiveData = MutableLiveData<Resource<User>>()
    val loginStatusLiveData: LiveData<Resource<User>>
        get() = _loginStatusLiveData

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userResource = userRepository.getUserByName(username, password)

                if (userResource is Resource.Success) {
                    val user = userResource.data!!
                    if (user.password == password) {
                        _loginStatusLiveData.postValue(Resource.Success(user))
                    } else {
                        _loginStatusLiveData.postValue(Resource.Error("Invalid credentials"))
                    }
                } else {
                    _loginStatusLiveData.postValue(Resource.Error("Invalid credentials"))
                }
            } catch (e: Exception) {
                _loginStatusLiveData.postValue(Resource.Error("An error occurred"))
            }
        }
    }

    fun clearLoginData() {
        _loginStatusLiveData.value = Resource.Loading(null)
    }
}