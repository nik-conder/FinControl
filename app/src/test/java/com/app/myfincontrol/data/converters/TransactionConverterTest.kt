package com.app.myfincontrol.data.converters

import com.app.myfincontrol.data.enums.TransactionCategories
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


class TransactionConverterTest {

    @Test
    fun fromIncomeCategory() {
        val incomeCategory = TransactionCategories.IncomeCategories.BUSINESS
        assertEquals(incomeCategory.name, TransactionConverter().fromIncomeCategory(incomeCategory))
    }

    @Test
    fun toIncomeCategory() {
        val incomeCategory = TransactionCategories.IncomeCategories.BUSINESS
        assertEquals(
            incomeCategory.name,
            TransactionConverter().toIncomeCategory(incomeCategory.name)
        )
    }
}