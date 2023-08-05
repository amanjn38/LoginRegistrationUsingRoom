package com.example.loginregistrationusingroom

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Annotate the class with @HiltAndroidApp to enable Hilt's dependency injection for the entire application.
// This annotation tells Hilt to generate necessary components and inject dependencies when needed.

@HiltAndroidApp
class LoginRegistrationApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}