package com.app.myfincontrol.presentation.compose.components

import androidx.compose.runtime.Composable
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType


@Composable
fun typeTransactionTranslate(type: TransactionType): Int {
    return when (type) {
        TransactionType.EXPENSE -> R.string.expense
        TransactionType.INCOME -> R.string.income
    }

}

@Composable
fun categoriesTranscript(category: TransactionCategories): Int {
    if (category is TransactionCategories.IncomeCategories) {
        return when (category) {
            TransactionCategories.IncomeCategories.SALARY -> R.string.salary
            TransactionCategories.IncomeCategories.FREELANCE -> R.string.freelance
            TransactionCategories.IncomeCategories.INVESTMENTS -> R.string.investments
            TransactionCategories.IncomeCategories.RENT -> R.string.rent
            TransactionCategories.IncomeCategories.BUSINESS -> R.string.business
            TransactionCategories.IncomeCategories.PART_TIME_JOBS -> R.string.part_time_jobs
            TransactionCategories.IncomeCategories.PENSION -> R.string.pension
            TransactionCategories.IncomeCategories.GIFTS -> R.string.gifts
            TransactionCategories.IncomeCategories.DIVIDENDS -> R.string.dividends
            TransactionCategories.IncomeCategories.SCHOLARSHIPS_AND_GRANTS -> R.string.scholarships_and_grants
            TransactionCategories.IncomeCategories.ALIMONY -> R.string.alimony
            TransactionCategories.IncomeCategories.SOCIAL_BENEFITS -> R.string.social_benefits
            TransactionCategories.IncomeCategories.COPYRIGHT_ROYALTIES -> R.string.copyright_royalties
            TransactionCategories.IncomeCategories.PRIZES_AND_LOTTERIES -> R.string.prizes_and_lotteries
            TransactionCategories.IncomeCategories.REFUNDS -> R.string.refunds
            TransactionCategories.IncomeCategories.OTHER_INCOME -> R.string.other_income
            else -> { R.string.error }
        }
    } else {
        return when (category) {
            TransactionCategories.ExpenseCategories.FOOD -> R.string.food
            TransactionCategories.ExpenseCategories.HOUSING -> R.string.housing
            TransactionCategories.ExpenseCategories.TRANSPORTATION -> R.string.transportation
            TransactionCategories.ExpenseCategories.HEALTH -> R.string.health
            TransactionCategories.ExpenseCategories.ENTERTAINMENT -> R.string.entertainment
            TransactionCategories.ExpenseCategories.CLOTHING_AND_FOOTWEAR -> R.string.clothing_and_footwear
            TransactionCategories.ExpenseCategories.PERSONAL_HYGIENE_AND_BEAUTY -> R.string.personal_hygiene_and_beauty
            TransactionCategories.ExpenseCategories.EDUCATION -> R.string.education
            TransactionCategories.ExpenseCategories.PETS -> R.string.pets
            TransactionCategories.ExpenseCategories.TRAVEL -> R.string.travel
            TransactionCategories.ExpenseCategories.CHILDREN -> R.string.children
            TransactionCategories.ExpenseCategories.CHARITY -> R.string.charity
            TransactionCategories.ExpenseCategories.TECHNOLOGY_AND_ELECTRONICS -> R.string.technology_and_electronics
            else -> { R.string.error }
        }
    }
}