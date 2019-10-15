package crypto.msd117c.com.cryptocurrency.domain.coins.repository.network

import crypto.msd117c.com.cryptocurrency.domain.coins.model.Coin
import io.reactivex.Observable
import javax.inject.Inject

class CoinsNetwork @Inject constructor(private val service: CoinsService) {

    fun retrieveLatestCoins(request: HashMap<String, String>): Observable<List<Coin>> = service.retrieveCoins(request)

}