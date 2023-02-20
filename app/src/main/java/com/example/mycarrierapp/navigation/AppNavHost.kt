package com.example.mycarrierapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.auth.LoginScreen
import com.example.mycarrierapp.ui.auth.SignupScreen
import com.example.mycarrierapp.ui.home.HomeScreen

@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(authViewModel, navController)
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(authViewModel, navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(authViewModel, navController)
        }
    }
}