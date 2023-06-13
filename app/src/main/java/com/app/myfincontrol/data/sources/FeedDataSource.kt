package com.app.myfincontrol.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.sources.database.TransactionDAO
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

class FeedDataSource @Inject constructor(
    private var transactionDAO: TransactionDAO
) : PagingSource<Int, Transactions>() {

    val limit = 10
    val lastID = 0
    override fun getRefreshKey(state: PagingState<Int, Transactions>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transactions> {
        return try {
            val nextPage = params.key ?: 0
            var response = emptyList<Transactions>()
            response = transactionDAO.getTransactions(nextPage, params.loadSize).map { it }
            val nextKey = if (response.isNotEmpty()) getNextTransactionId(response.first().id).toInt() else 0


            println("====== Запрос с source ======")
            params.let {
                println("it.key: ${it.key}")
                println("it.loadSize: ${it.loadSize}")
                println("it.placeholdersEnabled: ${it.placeholdersEnabled}")
            }
            println("nextPage: $nextPage, params.loadSize: ${params.loadSize}, nextKey: $nextKey")
            println(response)
            println("============")

            if (response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (nextPage == 1) null else nextPage - 1, // Paging 3 not used prevKey
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

    private suspend fun getNextTransactionId(currentID: Int): Long {
        return transactionDAO.getNextID(currentID)
    }
}