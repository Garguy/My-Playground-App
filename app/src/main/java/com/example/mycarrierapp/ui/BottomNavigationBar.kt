package com.example.mycarrierapp.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mycarrierapp.utils.Constants

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    
    BottomNavigation(
        
        // set background color
        backgroundColor = MaterialTheme.colorScheme.onSurface
    ) {
        
        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        
        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        
        // Bottom nav items we declared
        Constants.BottomNavItem.forEach { navItem ->
            
            // Place the bottom nav items
            BottomNavigationItem(
                
                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                
                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },
                
                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },
                
                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
    }
}