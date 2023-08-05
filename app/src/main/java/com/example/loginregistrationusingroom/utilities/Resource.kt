package com.example.loginregistrationusingroom.utilities

// Sealed class representing a resource that can have three states: Success, Error, or Loading.
// The class takes two generic parameters 'T' and 'R', where 'T' represents the data type and 'R' represents any additional information (e.g., error message).

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    // Success subclass of the Resource class, representing a successful result with associated data.
    class Success<T>(data: T) : Resource<T>(data)

    // Error subclass of the Resource class, representing an error result with an optional error message and associated data.
    // 'message' represents the error message, and 'data' contains any associated data (e.g., partial data when an error occurs).
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)


    // Loading subclass of the Resource class, representing a loading or in-progress state with associated data.
    // 'data' can be used to store any data related to the loading process (e.g., initial data).
    class Loading<T>(data: T?) : Resource<T>(data)
}
