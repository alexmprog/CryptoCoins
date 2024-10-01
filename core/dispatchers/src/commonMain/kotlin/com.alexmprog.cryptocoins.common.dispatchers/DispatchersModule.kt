package com.alexmprog.cryptocoins.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

const val CommonIoDispatcher = "IoDispatcher"
const val CommonDefaultDispatcher = "DefaultDispatcher"

val dispatchersModule
    get() = DI.Module("dispatchersModule") {
        bind<CoroutineDispatcher>(tag = CommonIoDispatcher) with singleton { Dispatchers.IO }
        bind<CoroutineDispatcher>(tag = CommonDefaultDispatcher) with singleton { Dispatchers.IO }
    }