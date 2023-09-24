package com.app.myfincontrol.data.sources.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.myfincontrol.data.AppDatabase
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class TransactionDAOTest {

    lateinit var db: AppDatabase
    lateinit var transactionDAO: TransactionDAO
    lateinit var sessionDAO: SessionDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        transactionDAO = db.transactionDao()
        sessionDAO = db.sessionDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertAndGetTransaction() = runBlocking {
        val transaction = Transaction(
            id = 1,
            profileId = 1,
            type = TransactionType.EXPENSE,
            amount = BigDecimal("10.00"),
            datetime = System.currentTimeMillis(),
            category = TransactionCategories.ExpenseCategories.CHARITY.name
        )

        val create = transactionDAO.insertTransaction(transaction)
        assertTrue(create >= 0L)

        val result = transactionDAO.getTransactions().first()
        assertEquals(transaction, result)
    }

    @Test
    @Throws(IOException::class)
    fun testGetLastTransaction() = runBlocking {
       repeat(10) {
           val transaction = Transaction(
               id = it + 1,
               profileId = 1,
               type = TransactionType.EXPENSE,
               amount = BigDecimal("10.00"),
               datetime = System.currentTimeMillis(),
               category = TransactionCategories.ExpenseCategories.CHARITY.name
           )
           val create = transactionDAO.insertTransaction(transaction)
           assertTrue(create >= 0L)
           println(transaction)
       }
    }

    @Test
    @Throws(IOException::class)
    fun testDeleteTransaction() = runBlocking {
        val transaction = Transaction(
            id = 1,
            profileId = 1,
            type = TransactionType.EXPENSE,
            amount = BigDecimal("10.00"),
            datetime = System.currentTimeMillis(),
            category = TransactionCategories.ExpenseCategories.CHARITY.name
        )

        val create = transactionDAO.insertTransaction(transaction)
        assertTrue(create >= 0L)
        val id = create.toInt()
        val result = transactionDAO.deleteTransaction(id)
        assertEquals(result, 1)
    }

    @Test
    @Throws(IOException::class)
    fun testGetBalance() = runBlocking {
        val profileId = 1

        // Create a session
        val session = Session(profile_id = profileId, timestamp = System.currentTimeMillis())
        sessionDAO.insertSession(session)

        // Creating multiple transactions
        val createTransaction = repeat(2) {
            val transaction = transactionDAO.insertTransaction(
                Transaction(
                    id = it + 1,
                    profileId = profileId,
                    type = TransactionType.INCOME,
                    amount = BigDecimal("5.00"),
                    datetime = it.toLong(),
                    category = TransactionCategories.IncomeCategories.BUSINESS.name
                )
            )
        }
        println(createTransaction)

        // Get balance of a profile
        val balance = transactionDAO.getBalance(datetime = 0).first()

        println(balance)
        assertEquals(balance, BigDecimal("10"))
    }

    @Test
    fun testDeleteAllTransactions() = runBlocking {
        val countTransactions = 10

        // Creating multiple transactions
        repeat(countTransactions) {
            transactionDAO.insertTransaction(
                Transaction(
                    id = it + 1,
                    profileId = 1,
                    type = TransactionType.INCOME,
                    amount = BigDecimal("5.00"),
                    datetime = it.toLong(),
                    category = TransactionCategories.IncomeCategories.BUSINESS.name
                )
            )
        }

        // Delete all transactions
        assertEquals(transactionDAO.deleteAllTransactions(), countTransactions)

    }

    @Test
    fun testGetLastID() = runBlocking {
        val countTransactions = 10
        repeat(countTransactions) {
            transactionDAO.insertTransaction(
                Transaction(
                    id = it + 1,
                    profileId = 1,
                    type = TransactionType.INCOME,
                    amount = BigDecimal("5.00"),
                    datetime = it.toLong(),
                    category = TransactionCategories.IncomeCategories.BUSINESS.name
                )
            )
        }
        val result = transactionDAO.getLastID()
        assertEquals(result, countTransactions.toLong())
    }

    @Test
    fun testGetNextID() = runBlocking {
        val countTransactions = 10
        repeat(countTransactions) {
            transactionDAO.insertTransaction(
                Transaction(
                    id = it + 1,
                    profileId = 1,
                    type = TransactionType.INCOME,
                    amount = BigDecimal("5.00"),
                    datetime = it.toLong(),
                    category = TransactionCategories.IncomeCategories.BUSINESS.name
                )
            )
        }
        val currentId = 5 // current id <= countTransactions
        val result = transactionDAO.getNextID(currentID = currentId)
        assertEquals(result, (currentId + 1).toLong())
    }

    @Test
    fun testGetChartTransactions() = runBlocking {

        val countTransactions = 10
        val startTime = System.currentTimeMillis() + 1000
        val endTime = System.currentTimeMillis() + 1000 * countTransactions

        repeat(countTransactions) {
            transactionDAO.insertTransaction(
                Transaction(
                    id = it + 1,
                    profileId = 1,
                    type = TransactionType.INCOME,
                    amount = BigDecimal("5.00"),
                    datetime = System.currentTimeMillis() + 1000 * it,
                    category = TransactionCategories.IncomeCategories.BUSINESS.name
                )
            )
        }

/*
        val result = transactionDAO.getChartTransactions(startTime = startTime, endTime = endTime)
        result.forEach {
            println(it)
        }

        */

        val get = transactionDAO.getTransactions()
        get.forEach {
            println(it)
        }
        println("startTime: $startTime endTime: $endTime")

        val result = transactionDAO.getChartTransactions(type = TransactionType.INCOME,startTime = 0, endTime = 169076081567234)
        result.forEach {
            println(it)
        }
        //todo

        //assertEquals(result.size, countTransactions)
    }
}