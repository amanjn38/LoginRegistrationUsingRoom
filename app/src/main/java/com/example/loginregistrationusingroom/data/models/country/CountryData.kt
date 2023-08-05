package com.example.loginregistrationusingroom.data.models.country

data class CountryData(
    val status: String,
    val statusCode: Int,
    val version: String,
    val access: String,
    val data: Map<String, CountryNames>
)
