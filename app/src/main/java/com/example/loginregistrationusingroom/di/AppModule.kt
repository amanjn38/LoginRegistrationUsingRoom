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

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        context: Context
    ): UserRepository {
        return UserRepository(context)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(
        context: Context
    ): CountryRepository {
        return CountryRepository(context)
    }
}