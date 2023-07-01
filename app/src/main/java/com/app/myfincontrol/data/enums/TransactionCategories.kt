package com.app.myfincontrol.data.enums

interface TransactionCategories {

    enum class IncomeCategories: TransactionCategories {
        INVESTS, SALARY
    }

    enum class ExpenseCategories: TransactionCategories {
        TRANSPORT, FOOD, CLOTHES, ENTERTAINMENT
    }
}