package com.alexmprog.cryptocoins.feature.home.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface HomeContent {

    @Composable
    operator fun invoke(component: HomeComponent, modifier: Modifier)
}