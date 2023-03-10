package com.example.mycarrierapp.data

sealed class FirebaseResource<out T> {
    data class Success<out T>(val result: T): FirebaseResource<T>()
    data class Failure(val exception: java.lang.Exception): FirebaseResource<Nothing>()
    object Loading: FirebaseResource<Nothing>()
}