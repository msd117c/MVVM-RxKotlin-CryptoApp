package crypto.msd117c.com.cryptocurrency.domain.coins.repository

import crypto.msd117c.com.cryptocurrency.domain.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import io.reactivex.Observable
import java.io.IOException
import javax.inject.Inject

class CoinsRepository @Inject constructor() {

    @Inject
    lateinit var network: CoinsNetwork

    @Throws(NoConnectionException::class, IOException::class)
    fun requestLatestCoins(): Observable<CoinResponse> =
        network.retrieveLatestCoins()

}