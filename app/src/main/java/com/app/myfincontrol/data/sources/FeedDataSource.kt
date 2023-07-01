package com.app.myfincontrol.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.sources.database.TransactionDAO
import kotlinx.coroutines.delay
import javax.inject.Inject

class FeedDataSource @Inject constructor(
    private val transactionDAO: TransactionDAO
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        // Используем текущее время в качестве ключа обновления
        return System.currentTimeMillis().toInt()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        delay(1000)
        val lastId = params.key ?: transactionDAO.getLastID()?.plus(1) ?: 0L // Если placeholdersEnabled == true, то lastId = 0
        val actualLastId = if (lastId == 1) 0 else lastId // Если lastId == 1, то заменяем его на 0 для первого запроса
        return try {
            val response = transactionDAO.getTransactions(actualLastId.toLong(), params.loadSize)
            val nextKey = if (response.isNotEmpty()) {
                response.last().id
            } else {
                null
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}