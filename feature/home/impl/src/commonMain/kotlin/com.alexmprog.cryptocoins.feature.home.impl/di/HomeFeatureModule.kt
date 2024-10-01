package com.alexmprog.cryptocoins.feature.home.impl.di

import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.alexmprog.cryptocoins.feature.home.api.HomeContent
import com.alexmprog.cryptocoins.feature.home.impl.DefaultHomeComponent
import com.alexmprog.cryptocoins.feature.home.impl.DefaultHomeContent
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val homeFeatureModule
    get() = DI.Module("homeFeatureModule") {
        bindSingleton<HomeComponent.Factory> {
            DefaultHomeComponent.Factory(instance(), instance())
        }
        bindSingleton<HomeContent> { DefaultHomeContent(instance(), instance()) }
    }