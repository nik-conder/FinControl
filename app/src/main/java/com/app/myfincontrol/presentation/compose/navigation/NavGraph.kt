package com.app.myfincontrol.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.myfincontrol.presentation.compose.screens.ChartsScreen
import com.app.myfincontrol.presentation.compose.screens.CreateProfileScreen
import com.app.myfincontrol.presentation.compose.screens.HomeScreen
import com.app.myfincontrol.presentation.compose.screens.LoginScreen
import com.app.myfincontrol.presentation.compose.screens.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Any
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.CreateProfile.route) {
            CreateProfileScreen(navController = navController)
        }

        composable(route = Screen.Charts.route) {
            ChartsScreen(navController = navController)
        }

    }
}