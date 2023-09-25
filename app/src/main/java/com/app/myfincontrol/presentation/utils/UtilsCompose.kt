package com.app.myfincontrol.presentation.utils

import android.icu.text.SimpleDateFormat
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.Currency
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

interface UtilsCompose {

    object Date: UtilsCompose {
        fun formatDate(date: Long): String {
            val sdf = SimpleDateFormat("dd.MM.yy HH:mm:ss", Locale.getDefault())
            return sdf.format(date)
        }
    }
    object Numbers: UtilsCompose {
        fun formatBigDecimalWithSpaces(number: BigDecimal): String {
            val decimalFormat = DecimalFormat("#,###.#####", DecimalFormatSymbols(Locale.getDefault()))
            return decimalFormat.format(number)
        }
    }

    object Symbols: UtilsCompose {
        fun currencySymbolComponent(currency: Currency): String {
            return when (currency) {
                Currency.USD -> "$"
                Currency.EUR -> "€"
                Currency.RUB -> "₽"
            }
        }
    }

    object Files: UtilsCompose {
        fun fileNameExport(): String {
            return "fin_control_${System.currentTimeMillis()}"
        }
    }

    object Transcription {
        fun typeTransactionTranslate(type: TransactionType): Int {
            return when (type) {
                TransactionType.EXPENSE -> R.string.expense
                TransactionType.INCOME -> R.string.income
            }
        }

        fun categoriesTranscript(category: TransactionCategories): Int {
            if (category is TransactionCategories.IncomeCategories) {
                return when (category) {
                    TransactionCategories.IncomeCategories.GIFTS -> R.string.gifts
                    TransactionCategories.IncomeCategories.PRIMARY_INCOME -> R.string.primary_income
                    TransactionCategories.IncomeCategories.ADDITIONAL_INCOME -> R.string.additional_income
                    TransactionCategories.IncomeCategories.BUSINESS -> R.string.business
                    TransactionCategories.IncomeCategories.INVESTMENTS -> R.string.investments
                    TransactionCategories.IncomeCategories.LEGAL_OBLIGATIONS_INCOME -> R.string.legal_obligations_income
                    TransactionCategories.IncomeCategories.SOCIAL_PAYMENTS -> R.string.social_payments
                    TransactionCategories.IncomeCategories.SCHOLARSHIPS_GRANTS -> R.string.scholarships_grants
                    TransactionCategories.IncomeCategories.PENSION -> R.string.pension
                    TransactionCategories.IncomeCategories.RETURNS -> R.string.returns
                    TransactionCategories.IncomeCategories.OTHER_INCOME -> R.string.other_income
                    else -> {
                        R.string.error
                    }
                }
            } else {
                return when (category) {
                    TransactionCategories.ExpenseCategories.HEALTH -> R.string.health
                    TransactionCategories.ExpenseCategories.TRANSPORT -> R.string.transport
                    TransactionCategories.ExpenseCategories.REAL_ESTATE -> R.string.real_estate
                    TransactionCategories.ExpenseCategories.FOOD -> R.string.food
                    TransactionCategories.ExpenseCategories.CLOTHING -> R.string.clothing
                    TransactionCategories.ExpenseCategories.FITNESS -> R.string.fitness
                    TransactionCategories.ExpenseCategories.BEAUTY_HEALTH -> R.string.beauty_health
                    TransactionCategories.ExpenseCategories.DEBT -> R.string.debt
                    TransactionCategories.ExpenseCategories.CREDIT -> R.string.credit
                    TransactionCategories.ExpenseCategories.CHARITY -> R.string.charity
                    TransactionCategories.ExpenseCategories.ELECTRONICS -> R.string.electronics
                    TransactionCategories.ExpenseCategories.COMMUNICATION -> R.string.communication
                    TransactionCategories.ExpenseCategories.HOUSEHOLD -> R.string.household
                    TransactionCategories.ExpenseCategories.SUBSCRIPTIONS -> R.string.subscriptions
                    TransactionCategories.ExpenseCategories.HOBBIES -> R.string.hobbies
                    TransactionCategories.ExpenseCategories.ENTERTAINMENT -> R.string.entertainment
                    TransactionCategories.ExpenseCategories.CHILDREN -> R.string.children
                    TransactionCategories.ExpenseCategories.FAMILY -> R.string.family
                    TransactionCategories.ExpenseCategories.RELATIONSHIPS -> R.string.relationships
                    TransactionCategories.ExpenseCategories.UTILITIES -> R.string.utilities
                    TransactionCategories.ExpenseCategories.PETS -> R.string.pets
                    TransactionCategories.ExpenseCategories.EDUCATION -> R.string.education
                    TransactionCategories.ExpenseCategories.PERSONAL_DEVELOPMENT -> R.string.personal_development
                    TransactionCategories.ExpenseCategories.LEGAL_OBLIGATIONS -> R.string.legal_obligations
                    TransactionCategories.ExpenseCategories.TAXES -> R.string.taxes
                    TransactionCategories.ExpenseCategories.TRAVEL -> R.string.travel
                    TransactionCategories.ExpenseCategories.OTHER_EXPENSES -> R.string.other_expenses
                    else -> {
                        R.string.error
                    }
                }
            }
        }
    }
}