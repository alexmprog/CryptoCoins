package com.alexmprog.cryptocoins.feature.home.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexmprog.cryptocoins.feature.coindetails.api.CoinDetailsContent
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListContent
import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.alexmprog.cryptocoins.feature.home.api.HomeContent
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

class DefaultHomeContent(
    private val coinListContent: CoinListContent,
    private val coinDetailsContent: CoinDetailsContent
) : HomeContent {

    @Composable
    override fun invoke(component: HomeComponent, modifier: Modifier) {
        Children(
            stack = component.stack,
            modifier = modifier,
            animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is HomeComponent.Child.CoinDetails -> coinDetailsContent(
                    component = child.component, modifier = Modifier.fillMaxSize(),
                )

                is HomeComponent.Child.CoinList -> coinListContent(
                    component = child.component, modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}