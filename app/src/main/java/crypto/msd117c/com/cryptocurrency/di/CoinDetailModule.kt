package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.data.coins.repository.CoinsRepositoryContract
import crypto.msd117c.com.cryptocurrency.domain.detail.CoinDetailUseCase
import crypto.msd117c.com.cryptocurrency.domain.detail.CoinDetailUseCaseContract
import dagger.Module
import dagger.Provides

@Module
class CoinDetailModule {

    @Provides
    fun provideCoinDetailUseCase(coinsRepositoryContract: CoinsRepositoryContract): CoinDetailUseCaseContract =
        CoinDetailUseCase(coinsRepositoryContract)

}