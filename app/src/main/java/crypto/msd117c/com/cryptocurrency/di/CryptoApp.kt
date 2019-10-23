package crypto.msd117c.com.cryptocurrency.di

import android.app.Application
import crypto.msd117c.com.cryptocurrency.di.core.CoreComponent
import crypto.msd117c.com.cryptocurrency.di.core.CoreComponentImplementation
import crypto.msd117c.com.cryptocurrency.di.repository.RepositoryComponent
import crypto.msd117c.com.cryptocurrency.di.repository.RepositoryComponentImplementation

class CryptoApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    lateinit var repositoryComponent: RepositoryComponent

    override fun onCreate() {
        super.onCreate()
        coreComponent = CoreComponentImplementation(this)

        repositoryComponent =
            RepositoryComponentImplementation(
                coreComponent
            )
    }
}