package crypto.msd117c.com.cryptocurrency.di.repository

import crypto.msd117c.com.cryptocurrency.di.network.NetworkComponent
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository

class RepositoryComponentImplementation(private val networkComponent: NetworkComponent): RepositoryComponent, NetworkComponent by networkComponent {
    override val coinsRepository by lazy {
        CoinsRepository(coinsNetwork)
    }
}