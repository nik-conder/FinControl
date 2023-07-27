package com.app.myfincontrol.presentation.compose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.presentation.compose.screens.CreateProfileScreen
import com.app.myfincontrol.presentation.compose.screens.HomeScreen
import com.app.myfincontrol.presentation.compose.screens.LoginScreen
import com.app.myfincontrol.presentation.compose.screens.SettingsScreen
import com.app.myfincontrol.presentation.compose.screens.StatisticsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    store: UserStore,
    navController: NavHostController,
    startDestination: String,
    snackBarHostState: SnackbarHostState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, store = store, snackBarHostState = snackBarHostState)
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController, store = store)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.CreateProfile.route) {
            CreateProfileScreen(navController = navController)
        }

        composable(route = Screen.Statistics.route) {
            StatisticsScreen(navController = navController, snackBarHostState = snackBarHostState)
        }

    }
}