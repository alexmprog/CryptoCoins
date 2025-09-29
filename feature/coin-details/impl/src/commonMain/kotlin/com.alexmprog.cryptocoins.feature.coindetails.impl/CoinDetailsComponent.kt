package com.alexmprog.cryptocoins.feature.coindetails.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexmprog.common.utils.resource.onSuccess
import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.domain.coins.model.CoinChart
import com.alexmprog.cryptocoins.domain.coins.model.CoinDetails
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

internal interface CoinDetailsComponentAdapter : CoinDetailsComponent {

    @Composable
    override fun Content(modifier: Modifier) = CoinDetailsComponentContent(this, modifier)

    data class State(
        val coin: Coin? = null,
        val coinDetails: CoinDetails? = null,
        val coinChart: CoinChart? = null
    )

    val state: StateFlow<State>

    fun onBack()
}

internal class CoinDetailsComponentImpl(
    componentContext: ComponentContext,
    coroutineDispatcher: CoroutineDispatcher,
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val getCoinChartUseCase: GetCoinChartUseCase,
    private val args: CoinDetailsComponent.Args,
    private val actions: CoinDetailsComponent.Actions,
) : CoinDetailsComponentAdapter, ComponentContext by componentContext {

    private val handler =
        instanceKeeper.getOrCreate { Handler(CoinDetailsComponentAdapter.State(coin = args.coin)) }

    override val state: StateFlow<CoinDetailsComponentAdapter.State> = handler.state.asStateFlow()

    private val scope = coroutineScope(coroutineDispatcher + SupervisorJob())

    init {
        scope.launch {
            getCoinDetailsUseCase(args.coin.id).onSuccess { details ->
                handler.state.update {
                    it.copy(coinDetails = details)
                }
            }

        }
        scope.launch {
            getCoinChartUseCase(args.coin.id).onSuccess { chart ->
                handler.state.update {
                    it.copy(coinChart = chart)
                }
            }
        }
    }

    override fun onBack() = actions.onBack()

    private class Handler(initialState: CoinDetailsComponentAdapter.State) :
        InstanceKeeper.Instance {
        val state = MutableStateFlow(initialState)
    }

    class Factory(
        private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
        private val getCoinChartUseCase: GetCoinChartUseCase,
        private val coroutineDispatcher: CoroutineDispatcher
    ) : CoinDetailsComponent.Factory {

        override fun invoke(
            componentContext: ComponentContext,
            args: CoinDetailsComponent.Args,
            actions: CoinDetailsComponent.Actions
        ): CoinDetailsComponent = CoinDetailsComponentImpl(
            componentContext = componentContext,
            coroutineDispatcher = coroutineDispatcher,
            getCoinDetailsUseCase = getCoinDetailsUseCase,
            getCoinChartUseCase = getCoinChartUseCase,
            args = args,
            actions = actions
        )
    }
}