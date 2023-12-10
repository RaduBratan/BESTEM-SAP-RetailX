package com.techstranding.retailx.ui.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.techstranding.retailx.navigation.Screen
import com.techstranding.retailx.ui.auth.account.AccountScreen
import com.techstranding.retailx.ui.auth.help.HelpScreen
import com.techstranding.retailx.ui.auth.login.LoginScreen

fun NavGraphBuilder.authApp(
    navController: NavHostController,
) {
    composable(route = Screen.Auth.AccountScreen.route) {
        AccountScreen(
            onLoginClick = {
                navController.navigate(
                    route = Screen.Auth.LoginScreen.route
                )
            },
            onHelpClick = {
                navController.navigate(
                    route = Screen.Auth.HelpScreen.route
                )
            },
        )
    }

    composable(route = Screen.Auth.HelpScreen.route) {
        HelpScreen(
            onBackClick = {
                navController.popBackStack()
            },
        )
    }

    composable(route = Screen.Auth.LoginScreen.route) {
        LoginScreen(
            onFinishClick = {
                navController.navigate(
                    route = Screen.Main.ProductsScreen.route
                ) {
                    popUpTo(Screen.Auth.AccountScreen.route) { inclusive = true }
                    popUpTo(Screen.Main.ProductsScreen.route) { inclusive = true }
                }
            },
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
}