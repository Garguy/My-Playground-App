package com.example.mycarrierapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycarrierapp.data.AuthRepository
import com.example.mycarrierapp.data.FirebaseResource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    
    private val _loginFlow = MutableStateFlow<FirebaseResource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<FirebaseResource<FirebaseUser>?> = _loginFlow
    
    private val _signupFlow = MutableStateFlow<FirebaseResource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<FirebaseResource<FirebaseUser>?> = _signupFlow
    
    val currentUser: FirebaseUser?
        get() = repository.currentUser
    
    init {
        if (repository.currentUser != null) {
            _loginFlow.value = FirebaseResource.Success(repository.currentUser!!)
        }
    }
    
    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = FirebaseResource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }
    
    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = FirebaseResource.Loading
        val result = repository.signup(name, email, password)
        _signupFlow.value = result
    }
    
    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }
}