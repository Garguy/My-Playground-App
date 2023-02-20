package com.example.mycarrierapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import com.example.mycarrierapp.navigation.ROUTE_HOME
import com.example.mycarrierapp.navigation.ROUTE_PROFILE
import com.example.mycarrierapp.navigation.ROUTE_SEARCH
import com.example.mycarrierapp.ui.BottomNavItem

object Constants {
    val BottomNavItem = listOf(
        BottomNavItem(
            label = ROUTE_HOME,
            icon = Icons.Filled.Home,
            route = ROUTE_HOME
        ),
        BottomNavItem(
            label = ROUTE_SEARCH,
            icon = Icons.Filled.Search,
            route = ROUTE_SEARCH
        ),
        BottomNavItem(
            label = ROUTE_PROFILE,
            icon = Icons.Filled.Person,
            route = ROUTE_PROFILE
        )
    )
}