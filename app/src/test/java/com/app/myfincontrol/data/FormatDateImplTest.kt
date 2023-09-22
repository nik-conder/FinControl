package com.app.myfincontrol.data

import org.junit.Test


class FormatDateImplTest {
    private val formatDateImpl = FormatDateImpl

    @Test
    fun getStartPeriod() {
        // todo
//        val sort = ChartSort.MONTH
//        val currentMonth = LocalDate.now().withDayOfMonth(1)
//        val startOfMonth =
//            LocalDateTime.of(currentMonth, LocalDateTime.MIN.toLocalTime()).toEpochSecond(
//                ZoneOffset.UTC
//            )
//        println("Date current: $startOfMonth")
//        assertEquals(startOfMonth, formatDateImpl.getStartPeriod(sort))
    }

    @Test
    fun getEndPeriod() {
        // todo
//        val sort = ChartSort.MONTH
//        val currentMonth = LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1)
//        val endOfMonth = LocalDateTime.of(currentMonth, LocalDateTime.MAX.toLocalTime())
//            .toEpochSecond(ZoneOffset.UTC)
//        println("Date next: $endOfMonth")
//        assertEquals(endOfMonth, formatDateImpl.getEndPeriod(sort))
    }
}