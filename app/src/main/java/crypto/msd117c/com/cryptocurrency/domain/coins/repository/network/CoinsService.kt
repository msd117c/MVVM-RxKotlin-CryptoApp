package crypto.msd117c.com.cryptocurrency.domain.coins.repository.network

import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET

interface CoinsService {

    @GET(Constants.END_POINT)
    fun retrieveCoins(): Observable<CoinResponse>

}