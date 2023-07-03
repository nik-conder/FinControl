package com.app.myfincontrol.presentation.viewModels.states

import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.enums.DarkMode

data class SettingsStates (
    val selectedProfile: Profile? = null,
    val isLoading: Boolean = false,
    val darkMode: Boolean = false
)