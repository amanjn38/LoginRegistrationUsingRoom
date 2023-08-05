package com.example.loginregistrationusingroom.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loginregistrationusingroom.dao.UserDao
import com.example.loginregistrationusingroom.data.models.user.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        // Function to get an instance of the UserDatabase.
        // It takes the application context as a parameter to access the database.
        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database" // The name of the database file
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
