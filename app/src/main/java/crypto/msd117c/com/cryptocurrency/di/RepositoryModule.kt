package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsService
import crypto.msd117c.com.cryptocurrency.util.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RepositoryModule {

    @Provides
    fun provideRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    @Provides
    fun provideCoinsRepository(retrofit: Retrofit): CoinsNetwork =
        CoinsNetwork(retrofit.create(CoinsService::class.java))
}