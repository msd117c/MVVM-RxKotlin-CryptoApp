package crypto.msd117c.com.cryptocurrency.modules.main

import androidx.annotation.VisibleForTesting
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import dagger.Module
import dagger.Provides

@Module
@VisibleForTesting
class TestMainModule(private val networkManager: NetworkManager) {

    @Provides
    fun provideNetworkManager(): NetworkManager = networkManager

}