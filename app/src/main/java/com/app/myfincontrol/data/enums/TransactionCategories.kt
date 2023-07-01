package com.app.myfincontrol.data.enums

interface TransactionCategories {

    enum class IncomeCategories: TransactionCategories {
        SALARY,
        FREELANCE,
        INVESTMENTS,
        RENT,
        BUSINESS,
        PART_TIME_JOBS,
        PENSION,
        GIFTS,
        DIVIDENDS,
        SCHOLARSHIPS_AND_GRANTS,
        ALIMONY,
        SOCIAL_BENEFITS,
        COPYRIGHT_ROYALTIES,
        PRIZES_AND_LOTTERIES,
        REFUNDS,
        OTHER_INCOME
    }

    enum class ExpenseCategories: TransactionCategories {
        FOOD,
        HOUSING,
        TRANSPORTATION,
        HEALTH,
        ENTERTAINMENT,
        CLOTHING_AND_FOOTWEAR,
        PERSONAL_HYGIENE_AND_BEAUTY,
        EDUCATION,
        PETS,
        TRAVEL,
        CHILDREN,
        CHARITY,
        TECHNOLOGY_AND_ELECTRONICS
    }
}