package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.myfincontrol.R
import com.app.myfincontrol.data.sources.FeedDataSource
import kotlinx.coroutines.launch

@Composable
fun FeedComponent(
    feedDataSource: FeedDataSource
) {
    val listState = rememberLazyListState()
    val scroll = rememberScrollState()

    val scope = rememberCoroutineScope()

    val feedDataSourceTest = remember {
        Pager(
            PagingConfig(pageSize = 10, prefetchDistance = 10),
            pagingSourceFactory = { feedDataSource }
        ).flow//.cachedIn(scope)
    }


    val feedDataSourcePaging = feedDataSourceTest.collectAsLazyPagingItems()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Row() {
            HeaderComponent(title = stringResource(id = R.string.title_feed) + " " + feedDataSourcePaging.itemCount)
        }
        Row {
            TextButton(onClick = {
                scope.launch {
                    listState.animateScrollToItem(feedDataSourcePaging.itemCount - 1)
                }
            }) {
                Text(text = "Test")
            }

        }

        Row() {
            LazyColumn(
                modifier = Modifier
                    //.fillMaxHeight(0.5f)
                    .height(600.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState,
            ) {
                items(items = feedDataSourcePaging.itemSnapshotList.items , key = { it.id }) { item ->
                    if (item != null) {
                        TransactionComponent(transactions = item)
                    } else {
                        Text(text = "Нет данных")
                    }
                }

                feedDataSourcePaging.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            // Показать индикатор загрузки данных
                            // Например, можно использовать компонент CircularProgressIndicator
                            item {
                                CircularProgressIndicator()
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            // Показать сообщение об ошибке
                            // Например, можно использовать компонент Snackbar
                            val errorState = feedDataSourcePaging.loadState.append as LoadState.Error
                            item {
                                Text(errorState.error.localizedMessage)
                            }
                        }
                        loadState.append is LoadState.NotLoading -> {
                            // Показать сообщение о том, что достигнут конец списка
                            // Например, можно использовать компонент Text
                            if (itemCount == 0) {
                                item {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentSize(Alignment.Center),
                                        text = "Список пуст"
                                    )
                                }
                            } else {
                                item {
                                    TextButton(onClick = {
                                        scope.launch {
                                            println("запущено")
                                            println(itemSnapshotList.items)
                                            feedDataSource.load(
                                                params = PagingSource.LoadParams.Append(
                                                    key = itemSnapshotList.lastOrNull()!!.id - 1,
                                                    loadSize = 30,
                                                    placeholdersEnabled = false
                                                )
                                            )
                                        }
                                    }) {
                                        Text(text = "прогрузить")
                                    }
                                }
                                scope.launch {
                                    val abc = feedDataSource.load(
                                        params = PagingSource.LoadParams.Append(
                                            key = itemSnapshotList.lastOrNull()!!.id - 1,
                                            loadSize = 30,
                                            placeholdersEnabled = false
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Box(
//                    modifier = Modifier
//                        .background(
//                            color = MaterialTheme.colorScheme.tertiaryContainer,
//                            shape = RoundedCornerShape(20.dp)
//                        )
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(16.dp)
//
//                ) {
//                    Box() {
//                        Text(text = "Нет данных")
//                    }
//
//                }
//            }
        }
    }
}