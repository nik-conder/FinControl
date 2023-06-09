package com.app.myfincontrol.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.app.myfincontrol.data.sources.database.TransactionDAO
import javax.inject.Inject

class FeedDataSource @Inject constructor(
    private val transactionDAO: TransactionDAO
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val nextPage = params.key ?: 1
            val response = transactionDAO.getTransactionById((nextPage - 1) * params.loadSize, params.loadSize)

            val nextKey = if (response.isNotEmpty()) {
                response.last().id
            } else null

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage > 1) nextPage - 1 else null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}