package com.alexmprog.thepets.core.network

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val networkModule
    get() = DI.Module("networkModule") {
        bind<HttpClient>() with singleton { createKtorClient() }
    }
