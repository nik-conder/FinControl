package com.app.myfincontrol.presentation.viewModels.states

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.presentation.compose.navigation.Screen

data class LoginState(
    val isLoading: Boolean = false,
    val startDestination: String = Screen.Login.route,
    val profilesList: List<Profile> = emptyList(),
    val selectedProfile: Int = 0,
    val darkMode: Boolean = false,
    val changeCurrencyAlert: Boolean = false
)