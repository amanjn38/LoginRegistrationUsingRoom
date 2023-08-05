package com.example.loginregistrationusingroom.data.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

//Model class to store data for a user
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val password: String,
    val country: String
)
