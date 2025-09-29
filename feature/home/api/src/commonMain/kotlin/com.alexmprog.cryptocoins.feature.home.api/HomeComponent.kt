package com.alexmprog.cryptocoins.feature.home.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext

interface HomeComponent {

    @Composable
    fun Content(modifier: Modifier)

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): HomeComponent
    }
}