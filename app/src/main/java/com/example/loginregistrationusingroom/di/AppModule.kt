package com.example.loginregistrationusingroom.di

import android.content.Context
import com.example.loginregistrationusingroom.data.repository.CountryRepository
import com.example.loginregistrationusingroom.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides the application context as a dependency for other classes.
    // It is annotated with @Provides to indicate that this function provides a dependency.
    // The @Singleton annotation ensures that only one instance of the Context is created and reused throughout the application.
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    // Provides the UserRepository as a dependency for other classes.
    @Provides
    @Singleton
    fun provideUserRepository(
        context: Context
    ): UserRepository {
        return UserRepository(context)
    }

    // Provides the CountryRepository as a dependency for other classes.
    @Provides
    @Singleton
    fun provideCountryRepository(
        context: Context
    ): CountryRepository {
        return CountryRepository(context)
    }
}