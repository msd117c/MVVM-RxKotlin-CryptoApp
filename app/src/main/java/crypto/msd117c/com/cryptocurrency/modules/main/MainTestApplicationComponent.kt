package crypto.msd117c.com.cryptocurrency.modules.main

import android.app.Application
import crypto.msd117c.com.cryptocurrency.TestNetworkModule
import crypto.msd117c.com.cryptocurrency.di.*
import crypto.msd117c.com.cryptocurrency.di.test.CryptoAppTest
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ActivityModule::class, AppModule::class, TestNetworkModule::class, RepositoryModule::class,
        TestMainModule::class]
)
interface MainTestApplicationComponent : AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun testModule(testModule: TestMainModule): Builder

        fun testNetworkModule(testNetworkModule: TestNetworkModule): Builder

        fun build(): MainTestApplicationComponent
    }

    fun inject(cryptoApp: CryptoAppTest)
}