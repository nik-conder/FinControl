package com.app.myfincontrol.presentation.viewModels.events

import com.app.myfincontrol.data.entities.Profile

sealed class LoginEvents {
    class Login(val profile: Profile) : LoginEvents()
    class CreateAccount(val profile: Profile) : LoginEvents()
    object NewProfile : LoginEvents()
}
