package com.example.mycarrierapp.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): FirebaseResource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): FirebaseResource<FirebaseUser>
    fun logout()
}