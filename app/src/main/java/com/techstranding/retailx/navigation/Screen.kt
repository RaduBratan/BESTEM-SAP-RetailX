package com.techstranding.retailx.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth") {
        object AccountScreen : Screen("account_screen")
        object HelpScreen : Screen("help_screen")
        object LoginScreen : Screen("login_screen")
    }

    object Main : Screen("main") {
        object ProductsScreen : Screen("products_screen")
        object RestocksScreen : Screen("restocks_screen")
        object CustomersScreen : Screen("customers_screen")
        object SettingsScreen : Screen("settings_screen")
    }

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}