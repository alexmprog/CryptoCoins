package com.alexmprog.cryptocoins.feature.coinlist.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.PagingData
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinsUseCase
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.Flow

internal interface CoinListComponentInternal : CoinListComponent {

    @Composable
    override fun Content(modifier: Modifier) = CoinListContent(this, modifier)

    val state: Flow<PagingData<Coin>>

    fun onCoinClicked(coin: Coin)
}

internal class CoinListComponentInternalImpl(
    componentContext: ComponentContext,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val actions: CoinListComponent.Actions
) : CoinListComponentInternal, ComponentContext by componentContext {

    private val handler = instanceKeeper.getOrCreate { Handler(getCoinsUseCase()) }

    override val state: Flow<PagingData<Coin>> = handler.state

    override fun onCoinClicked(coin: Coin) = actions.onCoinClicked(coin)

    private class Handler(initialState: Flow<PagingData<Coin>>) : InstanceKeeper.Instance {
        val state: Flow<PagingData<Coin>> = initialState
    }

    class Factory(private val getCoinsUseCase: GetCoinsUseCase) : CoinListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext, actions: CoinListComponent.Actions,
        ): CoinListComponent {
            return CoinListComponentInternalImpl(
                componentContext = componentContext,
                getCoinsUseCase = getCoinsUseCase,
                actions = actions
            )
        }
    }

}