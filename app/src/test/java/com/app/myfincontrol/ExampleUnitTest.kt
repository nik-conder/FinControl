package com.app.myfincontrol

import org.junit.Test

import org.junit.Assert.*
import org.junit.jupiter.api.Disabled

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Disabled("Disabled until bug #99 has been fixed")
class ExampleUnitTest {
    @Disabled("Disabled until bug #42 has been resolved")
    @Test
    fun `addition is Correct`() {
        assertEquals(4, 2 + 2)
        println("pizdec")
    }
}