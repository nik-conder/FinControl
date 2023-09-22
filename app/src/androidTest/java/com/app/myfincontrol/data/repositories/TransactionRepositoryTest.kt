package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import javax.inject.Inject

@HiltAndroidTest
class TransactionRepositoryTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Inject
    lateinit var transactionRepository: TransactionRepository

    @Test
    fun checkCreateTransaction() = runBlocking {
        val transaction = Transaction(
            id = 1,
            profileId = 1,
            type = TransactionType.EXPENSE,
            amount = BigDecimal("10.00"),
            datetime = System.currentTimeMillis(),
            category = TransactionCategories.ExpenseCategories.CHARITY.name
        )
        val result = transactionRepository.addTransaction(transaction)
        println("result: $result")
        assertEquals(1L, result)
    }

    @Test
    fun getBalance() = runBlocking {
        // todo
//        val transaction = Transaction(
//            id = 1,
//            profileId = 1,
//            type = TransactionType.EXPENSE,
//            amount = BigDecimal("12345.00"),
//            datetime = 0,
//            category = TransactionCategories.ExpenseCategories.CHARITY.name
//        )
//        transactionRepository.addTransaction(transaction)
//        val test = transactionRepository.getTransactions().first()
//        println("test: $test")
//        val balance = transactionRepository.getBalance(datetime = 0).first()
//        println("balance: $balance")
//        assertEquals(transaction.amount, balance)
    }

    @Test
    fun getChartTransactions() = runBlocking {
        // todo
    }

    @Test
    fun getTransactions() = runBlocking {
        // todo
    }

    @Test
    fun deleteTransaction() = runBlocking {
        val transaction = Transaction(
            id = 1,
            profileId = 1,
            type = TransactionType.EXPENSE,
            amount = BigDecimal("10.00"),
            datetime = System.currentTimeMillis(),
            category = TransactionCategories.ExpenseCategories.CHARITY.name
        )
        transactionRepository.addTransaction(transaction)
        val result = transactionRepository.deleteTransaction(id = 1)
        assertEquals(1, result)
    }
}