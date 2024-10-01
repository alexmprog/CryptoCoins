package com.alexmprog.cryptocoins.feature.home.impl

import com.alexmprog.cryptocoins.domain.coins.model.Coin
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val listComponentFactory: CoinListComponent.Factory,
    private val detailComponentFactory: CoinDetailsComponent.Factory,
) : HomeComponent, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, HomeComponent.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.CoinList,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): HomeComponent.Child = when (config) {
        Config.CoinList -> HomeComponent.Child.CoinList(
            listComponentFactory(
                componentContext = componentContext,
                onCoinClicked = { coin -> nav.pushNew(Config.CoinDetails(coin)) }
            )
        )

        is Config.CoinDetails -> HomeComponent.Child.CoinDetails(
            detailComponentFactory(
                componentContext = componentContext,
                coin = config.coin,
                onFinished = { nav.pop() },
            )
        )
    }


    @Serializable
    private sealed interface Config {

        @Serializable
        data object CoinList : Config

        @Serializable
        data class CoinDetails(val coin: Coin) : Config
    }

    class Factory(
        private val listComponentFactory: CoinListComponent.Factory,
        private val detailComponentFactory: CoinDetailsComponent.Factory,
    ) : HomeComponent.Factory {
        override fun invoke(componentContext: ComponentContext): HomeComponent {

            return DefaultHomeComponent(
                listComponentFactory = listComponentFactory,
                detailComponentFactory = detailComponentFactory,
                componentContext = componentContext,
            )
        }
    }
}