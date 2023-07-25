package com.app.myfincontrol.presentation.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberUtils {
    fun formatBigDecimalWithSpaces(number: BigDecimal): String {
        val decimalFormat = DecimalFormat("#,###.#####", DecimalFormatSymbols(Locale.getDefault()))
        return decimalFormat.format(number)
    }
}