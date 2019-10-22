package crypto.msd117c.com.cryptocurrency.di.repository

import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork

interface RepositoryComponent {
    val coinsNetwork: CoinsNetwork
    val coinsRepository: CoinsRepository
}