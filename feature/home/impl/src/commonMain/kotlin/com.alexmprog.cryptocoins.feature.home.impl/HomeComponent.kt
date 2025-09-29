package com.alexmprog.cryptocoins.feature.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

internal interface HomeComponentInternal : HomeComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class CoinList(val component: CoinListComponent) : Child
        class CoinDetails(val component: CoinDetailsComponent) : Child
    }

    @Composable
    override fun Content(modifier: Modifier) = HomeComponentContent(this, modifier)

}

internal class HomeComponentInternalImpl(
    componentContext: ComponentContext,
    private val listComponentFactory: CoinListComponent.Factory,
    private val detailComponentFactory: CoinDetailsComponent.Factory,
) : HomeComponentInternal, ComponentContext by componentContext {

    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, HomeComponentInternal.Child>> = childStack(
        source = nav,
        serializer = Config.serializer(),
        initialConfiguration = Config.CoinList,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): HomeComponentInternal.Child = when (config) {
        Config.CoinList -> HomeComponentInternal.Child.CoinList(
            listComponentFactory(
                componentContext = componentContext,
                actions = object : CoinListComponent.Actions {
                    override fun onCoinClicked(coin: Coin) {
                        nav.pushNew(Config.CoinDetails(coin))
                    }
                }
            )
        )

        is Config.CoinDetails -> HomeComponentInternal.Child.CoinDetails(
            detailComponentFactory(
                componentContext = componentContext,
                args = CoinDetailsComponent.Args(config.coin),
                actions = object : CoinDetailsComponent.Actions {
                    override fun onBack() {
                        nav.pop()
                    }
                },
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

            return HomeComponentInternalImpl(
                listComponentFactory = listComponentFactory,
                detailComponentFactory = detailComponentFactory,
                componentContext = componentContext,
            )
        }
    }
}