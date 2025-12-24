package com.alexmprog.cryptocoins.feature.coinlist.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.core.ui.components.CoinCard
import com.alexmprog.cryptocoins.core.ui.components.ErrorItem
import com.alexmprog.cryptocoins.core.ui.components.ErrorView
import com.alexmprog.cryptocoins.core.ui.components.LoadingItem
import com.alexmprog.cryptocoins.core.ui.components.LoadingView
import com.alexmprog.cryptocoins.core.ui.no_connection
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CoinListContent(component: CoinListComponentInternal, modifier: Modifier) {
    val items by rememberUpdatedState(component.state.collectAsLazyPagingItems())
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.cryptocurrency_prices)) },
            )
        }) { innerPaddings ->
        LazyPagingColumn(
            Modifier.padding(innerPaddings).fillMaxSize(),
            lazyListState,
            data = items
        ) { component.onCoinClicked(it) }
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

                refresh is LoadState.Loading -> {
                    item { LoadingView() }
                }

                refresh is LoadState.Error -> {
                    item {
                        ErrorView(
                            message = stringResource(com.alexmprog.cryptocoins.core.ui.Res.string.no_connection),
                            onClickRetry = { data.retry() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                append is LoadState.Error -> {
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