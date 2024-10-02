package com.alexmprog.cryptocoins.feature.coinlist.api

import app.cash.paging.PagingData
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow

interface CoinListComponent {

    val state: Flow<PagingData<Coin>>

    fun onCoinClicked(coin: Coin)

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext, onCoinClicked: (coin: Coin) -> Unit,
        ): CoinListComponent
    }
}