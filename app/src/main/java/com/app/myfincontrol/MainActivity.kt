package com.app.myfincontrol

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.compose.navigation.NavGraph
import com.app.myfincontrol.presentation.compose.navigation.Screen
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.example.compose.FinControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //private val viewModel: HomeViewModel by viewModels()
    //private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: LoginViewModel by viewModels()

        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            //val viewModel = viewModel.statesMain.collectAsState()
            val navController = rememberNavController()
            val viewModel = viewModel.states.collectAsState()

            val items = listOf(
                Screen.Profile,
                Screen.Home,
                Screen.Settings,
                Screen.Login,

                )

            FinControlTheme(
                //useDarkTheme = AppCompatDelegate.getDefaultNightMode()
            useDarkTheme = false
            ) {
                Scaffold(
                    bottomBar = {
                        NavigationComponent(navController = navController)
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        startDestination = viewModel.value.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinControlTheme {
        Greeting("Android")
    }
}