package com.app.myfincontrol.presentation.viewModels.states

import com.app.myfincontrol.data.entities.Profile

data class HomeStates(
    val isLoading: Boolean = false,
    val selectedProfile: Profile? = null
)
