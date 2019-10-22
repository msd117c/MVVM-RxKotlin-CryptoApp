package crypto.msd117c.com.cryptocurrency.domain.coins.repository

import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork

class CoinsRepository(private val network: CoinsNetwork) {

    suspend fun requestLatestCoins(): CoinResponse =
        network.retrieveLatestCoins()

}