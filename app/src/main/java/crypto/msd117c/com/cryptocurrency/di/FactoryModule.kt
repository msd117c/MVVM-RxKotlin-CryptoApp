package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FactoryModule {
    @Provides
    @Singleton
    fun providesRetrofitFactory(): RetrofitFactory {
        return RetrofitFactory()
    }
}