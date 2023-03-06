package com.example.mycarrierapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.screens.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    Log.d("RootNavGraph", "Root Nav Graph is called")
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController, authViewModel = authViewModel)
        composable(route = Graph.HOME) {
            HomeScreen(authViewModel = authViewModel)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}