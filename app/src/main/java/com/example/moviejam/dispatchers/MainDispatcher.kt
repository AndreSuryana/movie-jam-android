package com.example.moviejam.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainDispatcher : DispatcherProvider {

    override val main: CoroutineContext
        get() = Dispatchers.Main
    override val io: CoroutineContext
        get() = Dispatchers.IO
    override val default: CoroutineContext
        get() = Dispatchers.Default
    override val unconfined: CoroutineContext
        get() = Dispatchers.Unconfined
}