package com.app.myfincontrol.presentation.compose.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "homeScreen")
    object Settings : Screen(route = "settingsScreen")
    object Profile : Screen(route = "profileScreen")
    object Login : Screen(route = "loginScreen")
    object CreateProfile : Screen(route = "createProfileScreen")
}