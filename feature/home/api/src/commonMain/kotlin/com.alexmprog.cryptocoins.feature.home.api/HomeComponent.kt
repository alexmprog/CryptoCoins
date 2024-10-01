package com.alexmprog.cryptocoins.feature.home.api

import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsComponent
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface HomeComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class CoinList(val component: CoinListComponent) : Child
        class CoinDetails(val component: CoinDetailsComponent) : Child
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): HomeComponent
    }
}