package com.alexmprog.cryptocoins.feature.coinlist.impl.di

import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListComponent
import com.alexmprog.cryptocoins.feature.coinlist.api.CoinListContent
import com.alexmprog.cryptocoins.feature.coinlist.impl.DefaultCoinListComponent
import com.alexmprog.cryptocoins.feature.coinlist.impl.DefaultCoinListContent
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coinListFeatureModule
    get() = DI.Module("coinListFeatureModule") {
        bindSingleton<CoinListComponent.Factory> { DefaultCoinListComponent.Factory(instance()) }
        bindSingleton<CoinListContent> { DefaultCoinListContent() }
    }