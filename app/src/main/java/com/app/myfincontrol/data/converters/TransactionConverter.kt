package com.app.myfincontrol.data.converters

import androidx.room.TypeConverter
import com.app.myfincontrol.data.TransactionCategories

class TransactionConverter {

    @TypeConverter
    fun fromIncomeCategory(value: TransactionCategories.IncomeCategories): String {
        return value.name
    }

    @TypeConverter
    fun toIncomeCategory(value: String): TransactionCategories.IncomeCategories {
        return TransactionCategories.IncomeCategories.valueOf(value)
    }

    @TypeConverter
    fun fromExpenseCategory(value: TransactionCategories.ExpenseCategories): String {
        return value.name
    }

    @TypeConverter
    fun toExpenseCategory(value: String): TransactionCategories.ExpenseCategories {
        return TransactionCategories.ExpenseCategories.valueOf(value)
    }
}
