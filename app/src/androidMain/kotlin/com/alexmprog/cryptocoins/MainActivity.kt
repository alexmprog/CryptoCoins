package com.alexmprog.cryptocoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.alexmprog.cryptocoins.feature.home.api.HomeContent
import com.arkivanov.decompose.defaultComponentContext
import org.kodein.di.DIAware
import org.kodein.di.instance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val di = (application as DIAware).di
        val homeComponentFactory: HomeComponent.Factory by di.instance()
        val homeContent: HomeContent by di.instance()
        val homeComponent = homeComponentFactory(defaultComponentContext())
        setContent {
            App(homeComponent, homeContent)
        }
    }
}