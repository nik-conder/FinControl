package com.app.myfincontrol.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.sources.database.TransactionDAO
import javax.inject.Inject

class FeedDataSource @Inject constructor(
    private val transactionDAO: TransactionDAO
) : PagingSource<Int, Transactions>() {
    override fun getRefreshKey(state: PagingState<Int, Transactions>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transactions> {
        println("вызван load")
        return try {
            val nextPage = params.key ?: 0
            val response = transactionDAO.getTransactions(nextPage * params.loadSize, params.loadSize)
            val nextKey = response.lastOrNull()?.id

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage > 0) nextPage - 1 else null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}