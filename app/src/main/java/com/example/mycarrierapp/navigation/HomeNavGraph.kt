package com.example.mycarrierapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycarrierapp.ScreenContent
import com.example.mycarrierapp.ui.BottomBarScreen
import com.example.mycarrierapp.ui.auth.AuthViewModel

@Composable
fun HomeNavGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(
        navController = navController, route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Search.route) {
            ScreenContent(
                name = BottomBarScreen.Search.route,
                onClick = {}
            )
        }
        composable(route = BottomBarScreen.Home.route) {
            ScreenContent(
                name = BottomBarScreen.Home.route,
                onClick = {}
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                onClick = {}
            )
        }
    }
}