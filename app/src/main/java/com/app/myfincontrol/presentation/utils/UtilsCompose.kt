package com.app.myfincontrol.presentation.utils

import android.icu.text.SimpleDateFormat
import com.app.myfincontrol.data.enums.Currency
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

interface UtilsCompose {

    object Date: UtilsCompose {
        fun formatDate(date: Long): String {
            val sdf = SimpleDateFormat("dd.MM.yy HH:mm:ss", Locale.getDefault())
            return sdf.format(date)
        }
    }
    object Numbers: UtilsCompose {
        fun formatBigDecimalWithSpaces(number: BigDecimal): String {
            val decimalFormat = DecimalFormat("#,###.#####", DecimalFormatSymbols(Locale.getDefault()))
            return decimalFormat.format(number)
        }
    }

    object Symbols: UtilsCompose {
        fun currencySymbolComponent(currency: Currency): String {
            return when (currency) {
                Currency.USD -> "$"
                Currency.EUR -> "€"
                Currency.RUB -> "₽"
            }
        }
    }

    object Files: UtilsCompose {
        fun fileNameExport(): String {
            return "fin_control_${System.currentTimeMillis()}"
        }
    }
}