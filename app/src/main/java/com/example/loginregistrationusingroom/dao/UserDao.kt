package com.example.loginregistrationusingroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loginregistrationusingroom.data.models.user.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE name = :name AND password = :password")
    suspend fun getUserByNameAndPassword(name: String, password: String): User?
}