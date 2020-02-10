package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.data.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.domain.list.CoinsListUseCase
import crypto.msd117c.com.cryptocurrency.domain.list.CoinsListUseCaseContract
import dagger.Module
import dagger.Provides

@Module
class CoinsListModule {

    @Provides
    fun provideCoinsListUseCase(coinsRepository: CoinsRepository): CoinsListUseCaseContract =
        CoinsListUseCase(coinsRepository)

}