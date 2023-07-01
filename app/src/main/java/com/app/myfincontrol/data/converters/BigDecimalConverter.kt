package com.app.myfincontrol.data.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

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