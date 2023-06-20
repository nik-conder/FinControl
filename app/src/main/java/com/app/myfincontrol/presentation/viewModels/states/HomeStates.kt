package com.app.myfincontrol.presentation.viewModels.states

import android.icu.math.BigDecimal
import com.app.myfincontrol.data.entities.Profile

data class HomeStates(
    val isLoading: Boolean = false,
    val selectedProfile: Profile? = null,
    val balance: BigDecimal = BigDecimal.ZERO,
    val isLogin: Boolean = false,
    val hideBalance: Boolean = false
)
