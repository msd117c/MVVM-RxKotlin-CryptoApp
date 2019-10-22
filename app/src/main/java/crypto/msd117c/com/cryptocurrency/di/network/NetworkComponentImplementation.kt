package crypto.msd117c.com.cryptocurrency.di.network

import crypto.msd117c.com.cryptocurrency.di.core.CoreComponent
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsService

class NetworkComponentImplementation(private val coreComponent: CoreComponent) :
    NetworkComponent, CoreComponent by coreComponent {
    override val coinsNetwork by lazy {
        CoinsNetwork(retrofit.create(CoinsService::class.java))
    }
    override val coinsRepository by lazy {
        CoinsRepository(coinsNetwork)
    }
}