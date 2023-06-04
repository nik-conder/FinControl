package com.app.myfincontrol.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.myfincontrol.presentation.compose.screens.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

    }
}