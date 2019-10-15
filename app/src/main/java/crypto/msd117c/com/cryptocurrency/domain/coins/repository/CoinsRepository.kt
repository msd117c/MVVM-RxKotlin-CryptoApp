package crypto.msd117c.com.cryptocurrency.domain.coins.repository

import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import io.reactivex.Observable
import javax.inject.Inject

class CoinsRepository @Inject constructor() {

    @Inject
    lateinit var network: CoinsNetwork

    fun requestLatestCoins(request: HashMap<String, String>): Observable<CoinResponse> =
        network.retrieveLatestCoins(request)

}