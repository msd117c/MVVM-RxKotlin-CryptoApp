package crypto.msd117c.com.cryptocurrency.domain.coins.repository.network

import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import io.reactivex.Observable
import javax.inject.Inject

class CoinsNetwork @Inject constructor(private val service: CoinsService) {

    fun retrieveLatestCoins(): Observable<CoinResponse> =
        service.retrieveCoins()

}