package com.app.myfincontrol.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.sources.database.TransactionDAO
import javax.inject.Inject

class FeedDataSource @Inject constructor(
    private val transactionDAO: TransactionDAO
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.firstItemOrNull()?.id?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {

        return try {
            val nextPage = params.key ?: 0
            val response = transactionDAO.getTransactions(nextPage, params.loadSize)
            val nextID = if (response.isNotEmpty()) response.last().id + 1 else null
            val nextKey = if (response.isNotEmpty()) nextID else null

            if (response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = null, // Paging 3 not used prevKey
                    nextKey = nextKey
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}