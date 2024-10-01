package com.alexmprog.cryptocoins

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.alexmprog.cryptocoins.feature.home.api.HomeContent
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import org.kodein.di.instance

fun MainViewController() = ComposeUIViewController {
    val homeComponent = remember {
        val homeComponentFactory: HomeComponent.Factory by sharedCommonDI.instance()
        homeComponentFactory(DefaultComponentContext(ApplicationLifecycle()))
    }
    val homeContent = remember {
        val content: HomeContent by sharedCommonDI.instance()
        content
    }
    App(homeComponent, homeContent)
}