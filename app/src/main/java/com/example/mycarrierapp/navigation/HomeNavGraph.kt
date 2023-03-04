package com.example.mycarrierapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycarrierapp.ui.BottomBarScreen
import com.example.mycarrierapp.ui.ProfileScreen
import com.example.mycarrierapp.ui.SearchScreen
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.home.HomeScreen

@Composable
fun HomeNavGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController = navController, route = Graph.HOME,
    startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.Home.route) {
            Log.d("HomeNavGraph", "HomeScreen is being called")
            HomeScreen(authViewModel = authViewModel)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(authViewModel = authViewModel)
        }
    }
}