package crypto.msd117c.com.cryptocurrency.domain.coins.repository.network

import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse

class CoinsNetwork(private val service: CoinsService) {

    suspend fun retrieveLatestCoins(): CoinResponse =
        service.retrieveCoins()

}