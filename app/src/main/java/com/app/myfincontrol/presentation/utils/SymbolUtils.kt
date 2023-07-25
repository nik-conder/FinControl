package com.app.myfincontrol.presentation.utils

import com.app.myfincontrol.data.enums.Currency

object SymbolUtils {
    fun currencySymbolComponent(currency: Currency): String {
        return when (currency) {
            Currency.USD -> "$"
            Currency.EUR -> "€"
            Currency.RUB -> "₽"
        }
    }
}