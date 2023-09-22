package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.entities.Profile

sealed class LoginEvents {
    object Login : LoginEvents()
    class CreateAccount(val profile: Profile) : LoginEvents()
    class SelectProfile(val uid: Int) : LoginEvents()
    object ChangeCurrencyAlert : LoginEvents()
}
