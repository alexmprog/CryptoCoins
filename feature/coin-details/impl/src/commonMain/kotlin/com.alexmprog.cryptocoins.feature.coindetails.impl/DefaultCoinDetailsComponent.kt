package com.alexmprog.cryptocoins.feature.coindetails.impl

import com.alexmprog.common.utils.resource.onSuccess
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinChartUseCase
import com.alexmprog.cryptocoins.domain.coins.usecase.GetCoinDetailsUseCase
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DefaultCoinDetailsComponent(
    componentContext: ComponentContext,
    coroutineDispatcher: CoroutineDispatcher,
    coin: Coin,
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val getCoinChartUseCase: GetCoinChartUseCase,
    private val onFinished: () -> Unit,
) : CoinDetailsComponent, ComponentContext by componentContext {

    private val handler = instanceKeeper.getOrCreate { Handler(CoinDetailsComponent.State(coin = coin)) }

    override val state: StateFlow<CoinDetailsComponent.State> = handler.state.asStateFlow()

    private val scope = coroutineScope(coroutineDispatcher + SupervisorJob())

    init {
        scope.launch {
            getCoinDetailsUseCase(coin.id).onSuccess { details ->
                handler.state.update {
                    it.copy(coinDetails = details)
                }
            }

        }
        scope.launch {
            getCoinChartUseCase(coin.id).onSuccess { chart ->
                handler.state.update {
                    it.copy(coinChart = chart)
                }
            }
        }
    }

    override fun onBackPressed() = onFinished()

    private class Handler(initialState: CoinDetailsComponent.State) : InstanceKeeper.Instance {
        val state = MutableStateFlow(initialState)
    }

    class Factory(
        private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
        private val getCoinChartUseCase: GetCoinChartUseCase,
        private val coroutineDispatcher: CoroutineDispatcher
    ) : CoinDetailsComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            coin: Coin,
            onFinished: () -> Unit,
        ): CoinDetailsComponent = DefaultCoinDetailsComponent(
            componentContext = componentContext,
            coroutineDispatcher = coroutineDispatcher,
            coin = coin,
            getCoinDetailsUseCase = getCoinDetailsUseCase,
            getCoinChartUseCase = getCoinChartUseCase,
            onFinished = onFinished,
        )
    }
}