package com.app.myfincontrol.data.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.sources.database.TransactionDAO
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FeedDataSourceMediator @Inject constructor(
    private val transactionDAO: TransactionDAO
) : RemoteMediator<Int, Transactions>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Transactions>
    ): MediatorResult {

        println("Call to Mediator")

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    println("Mediator: REFRESH")
                    null
                }
                LoadType.PREPEND -> {

                    println("Mediator: PREPEND")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    println("Mediator: APPEND")
                    val lastItem = state.lastItemOrNull()

                    // We must explicitly check if the last item is `null` when appending,
                    // since passing `null` to networkService is only valid for initial load.
                    // If lastItem is `null` it means no items were loaded after the initial
                    // REFRESH and there are no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    lastItem.id
                }
            }

            val limit = 30
            val response = transactionDAO.getTransactions(lastID = loadKey!!, limit = limit)

            println("Mediator")
            println(loadKey)
            println(response)

            println("teeest")
            println(response.size < limit)

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}