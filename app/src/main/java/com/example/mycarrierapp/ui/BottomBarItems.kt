package com.example.mycarrierapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )
    
    object Search: BottomBarScreen(
        route = "MAP",
        title = "MAP",
        icon = Icons.Default.LocationOn
    )
    
    object Profile: BottomBarScreen(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.Person
    )
}
