package com.alexmprog.cryptocoins.feature.home.impl.di

import com.alexmprog.cryptocoins.feature.home.api.HomeComponent
import com.alexmprog.cryptocoins.feature.home.impl.HomeComponentImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val homeFeatureModule
    get() = DI.Module("homeFeatureModule") {
        bindSingleton<HomeComponent.Factory> {
            HomeComponentImpl.Factory(instance(), instance())
        }
    }