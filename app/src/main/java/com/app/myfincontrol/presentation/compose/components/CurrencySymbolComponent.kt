package com.app.myfincontrol.presentation.compose.components

import com.app.myfincontrol.data.enums.Currency


fun currencySymbolComponent(currency: Currency): String {
    return when (currency) {
        Currency.USD -> "$"
        Currency.EUR -> "€"
        Currency.RUB -> "₽"
    }
}