package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.data.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.data.coins.repository.CoinsRepositoryContract
import crypto.msd117c.com.cryptocurrency.data.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.data.coins.repository.network.CoinsNetworkContract
import crypto.msd117c.com.cryptocurrency.data.coins.repository.network.CoinsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RepositoryModule {

    @Provides
    fun provideCoinsNetwork(retrofit: Retrofit): CoinsNetworkContract =
        CoinsNetwork(retrofit.create(CoinsService::class.java))

    @Provides
    fun provideCoinsRepository(coinsNetworkContract: CoinsNetworkContract): CoinsRepositoryContract =
        CoinsRepository(coinsNetworkContract)
}