package com.example.mycarrierapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.mycarrierapp.navigation.RootNavigationGraph
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RootNavigationGraph(navController = rememberNavController(), authViewModel = authViewModel)
            }
        }
    }
}