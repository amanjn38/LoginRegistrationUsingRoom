package com.example.loginregistrationusingroom

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoginRegistrationApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}