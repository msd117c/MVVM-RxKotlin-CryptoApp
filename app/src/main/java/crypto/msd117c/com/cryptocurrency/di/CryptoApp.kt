package crypto.msd117c.com.cryptocurrency.di

import android.app.Application
import crypto.msd117c.com.cryptocurrency.di.core.CoreComponent
import crypto.msd117c.com.cryptocurrency.di.core.CoreComponentImplementation
import crypto.msd117c.com.cryptocurrency.di.network.NetworkComponent
import crypto.msd117c.com.cryptocurrency.di.network.NetworkComponentImplementation
import crypto.msd117c.com.cryptocurrency.di.repository.RepositoryComponent
import crypto.msd117c.com.cryptocurrency.di.repository.RepositoryComponentImplementation
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelComponent
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelComponentImplementation

class CryptoApp : Application() {

    private val coreComponent: CoreComponent by lazy {
        CoreComponentImplementation(this)
    }

    lateinit var networkComponent: NetworkComponent
    lateinit var repositoryComponent: RepositoryComponent
    lateinit var viewModelComponent: ViewModelComponent

    override fun onCreate() {
        super.onCreate()
        networkComponent =
            NetworkComponentImplementation(
                coreComponent
            )
        repositoryComponent =
            RepositoryComponentImplementation(
                networkComponent
            )
        viewModelComponent =
            ViewModelComponentImplementation(
                repositoryComponent
            )
    }
}