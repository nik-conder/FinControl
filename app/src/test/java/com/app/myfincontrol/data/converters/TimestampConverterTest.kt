package com.app.myfincontrol.data.converters

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.sql.Timestamp

class TimestampConverterTest {
    private val timestampConverter = TimestampConverter()

    @Test
    fun fromTimestamp() {
        val timestamp = Timestamp(System.currentTimeMillis())
        val result = timestampConverter.fromTimestamp(timestamp)
        assertEquals(timestamp.time, result)

    }

    @Test
    fun toTimestamp() {
        val timestamp = Timestamp(System.currentTimeMillis())
        val result = timestampConverter.toTimestamp(timestamp.time)
        assertEquals(timestamp, result)
    }
}