package com.app.myfincontrol.data.enums

interface TransactionCategories {

    enum class IncomeCategories : TransactionCategories {
        GIFTS,
        PRIMARY_INCOME,
        ADDITIONAL_INCOME,
        BUSINESS,
        INVESTMENTS,
        LEGAL_OBLIGATIONS_INCOME,
        SOCIAL_PAYMENTS,
        SCHOLARSHIPS_GRANTS,
        PENSION,
        RETURNS,
        OTHER_INCOME
    }

    enum class ExpenseCategories : TransactionCategories {
        HEALTH,
        TRANSPORT,
        REAL_ESTATE,
        FOOD,
        CLOTHING,
        FITNESS,
        BEAUTY_HEALTH,
        DEBT,
        CREDIT,
        CHARITY,
        ELECTRONICS,
        COMMUNICATION,
        HOUSEHOLD,
        SUBSCRIPTIONS,
        HOBBIES,
        ENTERTAINMENT,
        CHILDREN,
        FAMILY,
        RELATIONSHIPS,
        UTILITIES,
        PETS,
        EDUCATION,
        PERSONAL_DEVELOPMENT,
        LEGAL_OBLIGATIONS,
        TAXES,
        TRAVEL,
        OTHER_EXPENSES
    }
}
