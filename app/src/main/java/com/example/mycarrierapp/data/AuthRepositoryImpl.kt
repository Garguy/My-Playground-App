package com.example.mycarrierapp.data

import com.example.mycarrierapp.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
    
    override suspend fun login(email: String, password: String): FirebaseResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            FirebaseResource.Success(result.user!!)
        } catch (e: java.lang.Exception) {
            FirebaseResource.Failure(e)
        }
    }
    
    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): FirebaseResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )
            FirebaseResource.Success(result.user!!)
        } catch (e: java.lang.Exception) {
            FirebaseResource.Failure(e)
        }
    }
    
    override fun logout() {
        firebaseAuth.signOut()
    }
}