package com.example.mycarrierapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.auth.LoginScreen
import com.example.mycarrierapp.ui.auth.SignupScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            SignupScreen(authViewModel = authViewModel, navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login: AuthScreen(route = "LOGIN")
    object SignUp: AuthScreen(route = "SIGN_UP")
    object Forgot: AuthScreen(route = "FORGOT")
}