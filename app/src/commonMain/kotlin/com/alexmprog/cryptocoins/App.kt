package com.alexmprog.cryptocoins

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.alexmprog.cryptocoins.feature.home.api.HomeContent

@Composable
fun App(homeComponent: HomeComponent, homeContent: HomeContent) {
    MaterialTheme {
        homeContent(homeComponent, Modifier.fillMaxSize())
    }
}