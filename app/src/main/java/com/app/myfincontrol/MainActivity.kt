package com.app.myfincontrol

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.app.myfincontrol.presentation.compose.navigation.NavGraph
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.example.compose.FinControlTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var dataStore: UserStore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: LoginViewModel by viewModels()

        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            //val viewModel = viewModel.statesMain.collectAsState()
            val navController = rememberNavController()
            val states = viewModel.states.collectAsState()

            val darkMode = dataStore.darkModeState.collectAsState(initial = false)

            val snackBarHostState = SnackbarHostState()

            FinControlTheme(
                useDarkTheme = darkMode.value
            ) {
                NavGraph(
                    store = dataStore,
                    navController = navController,
                    startDestination = states.value.startDestination,
                    snackBarHostState = snackBarHostState
                )
            }
        }
    }
}