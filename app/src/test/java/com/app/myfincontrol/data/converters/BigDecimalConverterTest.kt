package com.app.myfincontrol.data.converters

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal

class BigDecimalConverterTest {

    private val bigDecimalConverterTest = BigDecimalConverter()

    @Test
    fun fromString() {
        val str = "123.456"
        bigDecimalConverterTest.fromString(str)
        assertEquals(BigDecimal("123.456"), bigDecimalConverterTest.fromString(str))
    }

    @Test
    fun bigDecimalToString() {
        val bigDecimal = BigDecimal("123.456")
        bigDecimalConverterTest.toString(bigDecimal)
        assertEquals("123.456", bigDecimalConverterTest.toString(bigDecimal))
    }
}