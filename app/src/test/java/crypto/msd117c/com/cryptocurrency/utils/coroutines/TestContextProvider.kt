package crypto.msd117c.com.cryptocurrency.utils.coroutines

import crypto.msd117c.com.cryptocurrency.di.viewmodel.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

open class TestContextProvider : CoroutineContextProvider() {
    override val Main = Dispatchers.Unconfined
    override val IO = Dispatchers.Unconfined
}