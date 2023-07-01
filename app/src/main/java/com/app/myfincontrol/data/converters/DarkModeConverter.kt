package com.app.myfincontrol.data.converters

import androidx.room.TypeConverter
import com.app.myfincontrol.data.enums.DarkMode

class DarkModeConverter {
    @TypeConverter
    fun fromString(value: String): DarkMode {
        return when(value) {
            "ENABLED" -> DarkMode.ENABLED
            "SYSTEM" -> DarkMode.SYSTEM
            else -> DarkMode.DISABLED
        }
    }

    @TypeConverter
    fun toString(value: DarkMode): String {
        return value.toString()
    }
}