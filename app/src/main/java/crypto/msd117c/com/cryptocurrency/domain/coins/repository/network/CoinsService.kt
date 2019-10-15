package crypto.msd117c.com.cryptocurrency.domain.coins.repository.network

import crypto.msd117c.com.cryptocurrency.domain.coins.model.Coin
import crypto.msd117c.com.cryptocurrency.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CoinsService {

    @GET(Constants.END_POINT)
    fun retrieveCoins(@QueryMap parameters: HashMap<String, String> = HashMap()): Observable<List<Coin>>

}