package com.example.mycarrierapp.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycarrierapp.navigation.HomeNavGraph
import com.example.mycarrierapp.ui.BottomBarScreen
import com.example.mycarrierapp.ui.auth.AuthViewModel
import com.example.mycarrierapp.ui.theme.AppTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel?,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Log.d("HomeNavGraph Call", "HomeNavGraph being called from HomeScreen")
            HomeNavGraph(navController = navController, authViewModel = authViewModel!!)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Search,
        BottomBarScreen.Home,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            screens.forEach { screens ->
                AddItem(
                    screen = screens,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title, color = MaterialTheme.colors.secondaryVariant)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = MaterialTheme.colors.secondaryVariant
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    AppTheme {
        HomeScreen(null, rememberNavController())
    }
}