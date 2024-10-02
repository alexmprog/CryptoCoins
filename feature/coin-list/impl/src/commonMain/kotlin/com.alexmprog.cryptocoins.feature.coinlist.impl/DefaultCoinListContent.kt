package com.alexmprog.cryptocoins.feature.coinlist.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.LazyPagingItems
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListContent
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.core.ui.components.CoinCard
import com.alexmprog.cryptocoins.core.ui.components.ErrorItem
import com.alexmprog.cryptocoins.core.ui.components.ErrorView
import com.alexmprog.cryptocoins.core.ui.components.LoadingItem
import com.alexmprog.cryptocoins.core.ui.components.LoadingView
import com.alexmprog.cryptocoins.core.ui.no_connection
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

class DefaultCoinListContent : CoinListContent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun invoke(component: CoinListComponent, modifier: Modifier) {
        val items by rememberUpdatedState(component.state.collectAsLazyPagingItems())
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(topBar = {
            TopAppBar(title = { Text(stringResource(Res.string.cryptocurrency_prices)) },
                actions = {
                    IconButton(onClick = {
                        items.refresh()
                        coroutineScope.launch { lazyListState.scrollToItem(0) }
                    }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                })
        }) { innerPaddings ->
            LazyPagingColumn(
                Modifier.padding(innerPaddings).fillMaxSize(),
                lazyListState,
                data = items
            ) { component.onCoinClicked(it) }
        }
    }
}

@Composable
private fun LazyPagingColumn(
    modifier: Modifier,
    lazyListState: LazyListState,
    data: LazyPagingItems<Coin>,
    onClick: (Coin) -> Unit
) {
    LazyColumn(modifier = modifier, state = lazyListState) {
        items(data.itemCount, key = data.itemKey { it.id }) { index ->
            data[index]?.let {
                CoinCard(
                    modifier = Modifier.fillMaxSize().wrapContentHeight(),
                    name = it.name,
                    symbol = it.symbol,
                    imageUrl = it.imageUrl,
                    price = it.currentPrice,
                    priceChangePercentage24h = it.priceChangePercentage24h,
                ) {
                    onClick(it)
                }
            }
        }
        data.loadState.apply {
            when {

                refresh is LoadStateLoading -> {
                    item { LoadingView() }
                }

                refresh is LoadStateError -> {
                    item {
                        ErrorView(
                            message = stringResource(com.alexmprog.cryptocoins.core.ui.Res.string.no_connection),
                            onClickRetry = { data.retry() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                append is LoadStateLoading -> {
                    item { LoadingItem() }
                }

                append is LoadStateError -> {
                    item {
                        ErrorItem(
                            message = stringResource(com.alexmprog.cryptocoins.core.ui.Res.string.no_connection),
                            onClickRetry = { data.retry() },
                        )
                    }
                }
            }
        }
    }
}