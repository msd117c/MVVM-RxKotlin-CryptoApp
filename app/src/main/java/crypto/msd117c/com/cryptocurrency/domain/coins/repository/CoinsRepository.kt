package crypto.msd117c.com.cryptocurrency.domain.coins.repository

import crypto.msd117c.com.cryptocurrency.base.domain.model.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import java.io.IOException

class CoinsRepository(private val network: CoinsNetwork) {

    @Throws(IOException::class, NoConnectionException::class)
    suspend fun requestLatestCoins(): CoinResponse =
        network.retrieveLatestCoins()

}