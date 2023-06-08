package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.entities.Profile

sealed class HomeEvents {
    class Login(val profile: Profile) : HomeEvents()
}
