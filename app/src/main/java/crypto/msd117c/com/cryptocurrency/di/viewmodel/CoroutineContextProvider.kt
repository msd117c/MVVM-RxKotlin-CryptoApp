package crypto.msd117c.com.cryptocurrency.di.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider {
    open val Main: CoroutineContext by lazy {
        Dispatchers.Main
    }

    open val IO: CoroutineContext by lazy {
        Dispatchers.IO
    }
}