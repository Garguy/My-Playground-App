package com.example.mycarrierapp.data

sealed class Resource<out T> {
    data class Success<out T>(val result: T): Resource<T>()
    data class Failure(val exception: java.lang.Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}