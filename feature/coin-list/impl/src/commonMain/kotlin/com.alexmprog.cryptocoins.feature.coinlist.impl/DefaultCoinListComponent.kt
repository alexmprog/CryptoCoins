package com.alexmprog.cryptocoins.feature.coinlist.impl

import app.cash.paging.PagingData
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinsUseCase
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow

class DefaultCoinListComponent(
    componentContext: ComponentContext,
    getCoinsUseCase: GetCoinsUseCase,
    private val coinClicked: (coin: Coin) -> Unit
) : CoinListComponent, ComponentContext by componentContext {

    override val model: Flow<PagingData<Coin>> = getCoinsUseCase()

    override fun onCoinClicked(coin: Coin) = coinClicked(coin)

    class Factory(
        private val getCoinsUseCase: GetCoinsUseCase,
    ) : CoinListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext, onCoinClicked: (coin: Coin) -> Unit,
        ): CoinListComponent {
            return DefaultCoinListComponent(
                componentContext = componentContext,
                getCoinsUseCase = getCoinsUseCase,
                coinClicked = onCoinClicked,
            )
        }
    }

}