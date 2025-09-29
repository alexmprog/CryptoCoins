package com.alexmprog.cryptocoins.feature.home.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
internal fun HomeComponentContent(component: HomeComponentInternal, modifier: Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is HomeComponentInternal.Child.CoinDetails -> child.component.Content(Modifier.fillMaxSize())
            is HomeComponentInternal.Child.CoinList -> child.component.Content(Modifier.fillMaxSize())
        }
    }
}