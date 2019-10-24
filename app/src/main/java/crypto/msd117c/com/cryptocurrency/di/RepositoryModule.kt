package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RepositoryModule {

    @Provides
    fun provideCoinsRepository(retrofit: Retrofit): CoinsNetwork =
        CoinsNetwork(retrofit.create(CoinsService::class.java))
}