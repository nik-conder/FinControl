package com.app.myfincontrol.presentation.viewModels.states

import com.app.myfincontrol.data.entities.Profile

data class SettingsStates (
    val selectedProfile: Profile? = null,
    val isLoading: Boolean = false
)