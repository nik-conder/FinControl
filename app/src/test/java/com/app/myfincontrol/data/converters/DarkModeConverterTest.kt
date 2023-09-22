package com.app.myfincontrol.data.converters

import com.app.myfincontrol.data.enums.DarkMode
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class DarkModeConverterTest {
    private val darkModeConverter = DarkModeConverter()

    @Test
    fun `fromString`() {
        val strEnabled = "ENABLED"
        val strSystem = "SYSTEM"
        val strDisabled = "DISABLED"

        darkModeConverter.fromString(strEnabled)
        darkModeConverter.fromString(strSystem)
        darkModeConverter.fromString(strDisabled)

        assertEquals(DarkMode.ENABLED, darkModeConverter.fromString(strEnabled))
        assertEquals(DarkMode.SYSTEM, darkModeConverter.fromString(strSystem))
        assertEquals(DarkMode.DISABLED, darkModeConverter.fromString(strDisabled))
    }

    @Test
    fun `darkModeToString`() {
        val darkMode = DarkMode.ENABLED
        assertEquals("ENABLED", darkModeConverter.toString(darkMode))
    }
}