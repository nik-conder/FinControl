package com.app.myfincontrol

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.app.myfincontrol.presentation.compose.components.NavigationComponent
import com.app.myfincontrol.presentation.compose.navigation.NavGraph
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.example.compose.FinControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: LoginViewModel by viewModels()

        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            //val viewModel = viewModel.statesMain.collectAsState()
            val navController = rememberNavController()
            val viewModel = viewModel.states.collectAsState()

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