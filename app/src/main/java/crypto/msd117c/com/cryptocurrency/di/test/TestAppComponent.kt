package crypto.msd117c.com.cryptocurrency.di.test

import crypto.msd117c.com.cryptocurrency.di.AppComponent
import crypto.msd117c.com.cryptocurrency.di.CoinDetailModule
import crypto.msd117c.com.cryptocurrency.di.CoinsListModule
import crypto.msd117c.com.cryptocurrency.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoinsListModule::class, CoinDetailModule::class, NetworkTestModule::class, RepositoryModule::class])
interface TestAppComponent : AppComponent