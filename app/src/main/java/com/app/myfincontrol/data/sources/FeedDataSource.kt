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
    private var lastLoadedKey: Int? = null

    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return lastLoadedKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        println("=====")
        delay(400)
        val lastId = params.key ?: transactionDAO.getLastID()?.plus(1) ?: 0L
        val actualLastId = if (lastId == 1) 0 else lastId
        println("lastId: $lastId, actualLastId: $actualLastId")
        return try {
            val response = transactionDAO.getTransactions(actualLastId.toLong(), params.loadSize)

            println(response)

            lastLoadedKey = response.lastOrNull()?.id

            val nextKey = if (response.isNotEmpty()) { response.last().id } else { null }
            println("nextKey: $nextKey")

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
        println("=====")
    }
}