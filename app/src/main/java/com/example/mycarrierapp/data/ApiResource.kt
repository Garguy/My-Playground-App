package com.example.mycarrierapp.data

sealed class ApiResource<out T> {
    data class Success<out T>(val data: T) : ApiResource<T>()
    data class Failure(val exception: Exception) : ApiResource<Nothing>()
}