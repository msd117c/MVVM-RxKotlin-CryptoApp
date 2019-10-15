package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.BuildConfig
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsService
import crypto.msd117c.com.cryptocurrency.util.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RepositoryModule {

    @Provides
    fun provideRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(provideHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    @Provides
    fun provideCoinsRepository(retrofit: Retrofit): CoinsNetwork =
        CoinsNetwork(retrofit.create(CoinsService::class.java))

    private fun provideHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        // Set timeout
        client.readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        return client.build()
    }
}