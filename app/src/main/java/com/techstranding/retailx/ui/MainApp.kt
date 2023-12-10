package com.techstranding.retailx.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.techstranding.retailx.navigation.Screen
import com.techstranding.retailx.ui.customers.CustomersScreen
import com.techstranding.retailx.ui.products.ProductsScreen
import com.techstranding.retailx.ui.restocks.RestocksScreen
import com.techstranding.retailx.ui.settings.SettingsScreen

fun NavGraphBuilder.mainApp(
    navController: NavHostController,
) {
    composable(route = Screen.Main.ProductsScreen.route) {
        /*Scaffold(
            bottomBar = {
                BottomBar(navController)
            }
        ) {*/
            ProductsScreen(
                // modifier = Modifier.padding(it),
                onAnomaliesSeeAllClick = { },
                onDemandsSeeAllClick = { },
            )
        // }
    }

    composable(route = Screen.Main.RestocksScreen.route) {
        /*Scaffold(
            bottomBar = {
                BottomBar(navController)
            }
        ) {*/
            RestocksScreen(
                // modifier = Modifier.padding(it)
            )
        // }
    }

    composable(route = Screen.Main.CustomersScreen.route) {
        /*Scaffold(
            bottomBar = {
                BottomBar(navController)
            }
        ) {*/
            CustomersScreen(
                // modifier = Modifier.padding(it)
            )
        // }
    }

    composable(route = Screen.Main.SettingsScreen.route) {
        /*Scaffold(
            bottomBar = {
                BottomBar(navController)
            }
        ) {*/
            SettingsScreen(
                // modifier = Modifier.padding(it),
            )
        // }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Main.ProductsScreen,
        Screen.Main.RestocksScreen,
        Screen.Main.CustomersScreen,
        Screen.Main.SettingsScreen
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation {
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = { },
                label = { },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}