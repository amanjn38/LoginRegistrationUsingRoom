package com.example.loginregistrationusingroom.data.repository

import android.content.Context
import com.example.loginregistrationusingroom.data.models.country.CountryData
import com.google.gson.Gson
import java.io.IOException

class CountryRepository(private val context: Context) {
    fun readCountriesFromAssets(context: Context): List<String> {
        val countryNames = mutableListOf<String>()

        try {
            val inputStream = context.assets.open("countries.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val jsonString = String(buffer, Charsets.UTF_8)
            val gson = Gson()
            val countryData = gson.fromJson(jsonString, CountryData::class.java)

            for ((code, country) in countryData.data) {
                countryNames.add(country.country)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return countryNames
    }
}