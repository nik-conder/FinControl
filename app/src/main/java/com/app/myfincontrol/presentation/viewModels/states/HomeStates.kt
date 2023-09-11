package com.app.myfincontrol.presentation.viewModels.states

import java.math.BigDecimal
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Transaction

data class HomeStates(
    val isLoading: Boolean = false,
    val selectedProfile: Profile? = null,
    val balance: BigDecimal = BigDecimal.ZERO,
    val isLogin: Boolean = false,
    val hideBalanceState: Boolean = false,
    val debugModeShow: Boolean = false,
    val showBottomSheet: Boolean = false
)
