package crypto.msd117c.com.cryptocurrency.di.network

import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork

interface NetworkComponent {
    val coinsNetwork: CoinsNetwork
    val coinsRepository: CoinsRepository
}