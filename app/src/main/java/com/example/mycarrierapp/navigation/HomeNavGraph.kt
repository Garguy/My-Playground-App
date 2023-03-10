package com.example.mycarrierapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycarrierapp.PokemonListScreen
import com.example.mycarrierapp.ui.BottomBarScreen
import com.example.mycarrierapp.ui.ProfileScreen
import com.example.mycarrierapp.ui.SearchScreen
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.screens.PokemonDetailScreen

@Composable
fun HomeNavGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(
        navController = navController, route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.Home.route) {
            PokemonListScreen(
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(
                authViewModel = authViewModel
            )
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Detail.route
    ) {
        composable(
            route = DetailsScreen.Detail.route,
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )) {
            val dominantColor = remember {
                val color = it.arguments?.getInt("dominantColor")
                color?.let { Color(it) } ?: Color.White
            }
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            PokemonDetailScreen(dominantColor, pokemonName)
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Detail : DetailsScreen(route = "detail/{dominantColor}/{pokemonName}")
}