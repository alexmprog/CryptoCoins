package com.alexmprog.cryptocoins.feature.coinlist.impl

import app.cash.paging.PagingData
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinsUseCase
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.Flow

class DefaultCoinListComponent(
    componentContext: ComponentContext,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val coinClicked: (coin: Coin) -> Unit
) : CoinListComponent, ComponentContext by componentContext {

    private val handler = instanceKeeper.getOrCreate { Handler(getCoinsUseCase()) }

    override val state: Flow<PagingData<Coin>> = handler.state

    override fun onCoinClicked(coin: Coin) = coinClicked(coin)

    private class Handler(initialState: Flow<PagingData<Coin>>) : InstanceKeeper.Instance {
        val state: Flow<PagingData<Coin>> = initialState
    }

    class Factory(private val getCoinsUseCase: GetCoinsUseCase, ) : CoinListComponent.Factory {
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