package com.app.myfincontrol.presentation.viewModels.events

sealed class SettingsEvents {
    object DarkMode : SettingsEvents()
    object DeleteProfile : SettingsEvents()
}
