package com.app.myfincontrol.data

enum class TransactionType {
    INCOME, EXPENSE
}

interface TransactionCategories {

    enum class IncomeCategories: TransactionCategories {
        INVESTS, SALARY
    }

    enum class ExpenseCategories: TransactionCategories {
        TRANSPORT, FOOD, CLOTHES, ENTERTAINMENT
    }
}