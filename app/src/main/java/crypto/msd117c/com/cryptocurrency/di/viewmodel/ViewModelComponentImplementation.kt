package crypto.msd117c.com.cryptocurrency.di.viewmodel

import crypto.msd117c.com.cryptocurrency.di.repository.RepositoryComponent

class ViewModelComponentImplementation(private val repositoryComponent: RepositoryComponent) :
    ViewModelComponent, RepositoryComponent by repositoryComponent {
    override val viewModelFactory by lazy {
        ViewModelFactory(coinsRepository)
    }
}