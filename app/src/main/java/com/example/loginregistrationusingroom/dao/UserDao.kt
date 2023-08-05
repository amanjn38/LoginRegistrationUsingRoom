package com.example.loginregistrationusingroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loginregistrationusingroom.data.models.user.User

@Dao
interface UserDao {

    // Method to insert a new user into the database.
    @Insert
    suspend fun insertUser(user: User)

    // Method to retrieve a user from the database based on their name and password.
    @Query("SELECT * FROM user WHERE name = :name AND password = :password")
    suspend fun getUserByNameAndPassword(name: String, password: String): User?
}