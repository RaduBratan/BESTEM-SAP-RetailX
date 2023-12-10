package com.techstranding.retailx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.techstranding.retailx.ui.auth.authApp
import com.techstranding.retailx.ui.mainApp

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: NavigationViewModel = hiltViewModel()
    val startDestination = viewModel.startDestination.collectAsState()

    startDestination.value?.let {
        NavHost(
            navController = navController,
            startDestination = it,
        ) {
            navigation(
                route = Screen.Auth.route,
                startDestination = Screen.Auth.AccountScreen.route
            ) {
                authApp(
                    navController = navController,
                )
            }

            navigation(
                route = Screen.Main.route,
                startDestination = Screen.Main.ProductsScreen.route
            ) {
                mainApp(
                    navController = navController,
                )
            }
        }
    }
}