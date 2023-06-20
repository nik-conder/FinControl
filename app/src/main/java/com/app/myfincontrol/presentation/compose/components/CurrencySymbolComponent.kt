package com.app.myfincontrol.presentation.compose.components

import androidx.compose.runtime.Composable
import com.app.myfincontrol.data.Currency

fun currencySymbolComponent(currency: Currency): String {
    return when (currency) {
        Currency.USD -> "$"
        Currency.EUR -> "€"
        Currency.RUB -> "₽"
    }
}