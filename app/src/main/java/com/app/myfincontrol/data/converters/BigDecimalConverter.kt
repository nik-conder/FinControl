package com.app.myfincontrol.data.converters

import android.icu.math.BigDecimal
import androidx.room.TypeConverter

class BigDecimalConverter {
    @TypeConverter
    fun fromString(value: String): BigDecimal {
        return BigDecimal(value)
    }

    @TypeConverter
    fun toString(value: BigDecimal): String {
        return value.toString()
    }
}