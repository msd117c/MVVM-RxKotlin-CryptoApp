package crypto.msd117c.com.cryptocurrency.di

import android.app.Application
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.ui.CoinDetailFragment
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.ui.CoinsListFragment
import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoinsListModule::class, CoinDetailModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(coinsListFragment: CoinsListFragment)

    fun inject(coinDetailFragment: CoinDetailFragment)
}