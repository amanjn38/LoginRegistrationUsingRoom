package com.example.loginregistrationusingroom.data.repository

import android.content.Context
import com.example.loginregistrationusingroom.dao.UserDao
import com.example.loginregistrationusingroom.data.models.user.User
import com.example.loginregistrationusingroom.di.UserDatabase
import com.example.loginregistrationusingroom.utilities.Resource

class UserRepository(context: Context) {
    private val userDao: UserDao

    // The constructor initiates the UserRepository and sets up the UserDao.
    // It takes a 'context' parameter to access the application context.
    init {
        val db = UserDatabase.getInstance(context)
        userDao = db.userDao()
    }

    // Function to register a new user.
    suspend fun registerUser(user: User) {
        // Simulate user registration
        // Insert the user  into the Room database (In-Memory Storage)
        userDao.insertUser(user)
    }

    // Function to retrieve a user by their name and password.
    suspend fun getUserByName(name: String, password: String): Resource<User> {
        return try {
            // Attempt to get the user from the Room database using the UserDao.
            val user = userDao.getUserByNameAndPassword(name, password)
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("User not found")
            }
        } catch (e: Exception) {
            Resource.Error("An error occurred")
        }
    }
}
