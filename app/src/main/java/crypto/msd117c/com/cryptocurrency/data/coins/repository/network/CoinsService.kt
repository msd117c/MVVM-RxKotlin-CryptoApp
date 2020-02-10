package crypto.msd117c.com.cryptocurrency.data.coins.repository.network

import com.google.gson.JsonElement
import crypto.msd117c.com.cryptocurrency.constants.ApiConstants
import crypto.msd117c.com.cryptocurrency.data.model.list.Coin
import crypto.msd117c.com.cryptocurrency.data.model.list.CoinResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsService {

    @GET(ApiConstants.LATEST_COINS_ENDPOINT)
    fun retrieveCoins(): Single<CoinResponse>

    @GET(ApiConstants.COIN_DETAIL_ENDPOINT)
    fun retrieveCoinDetail(@Query(ApiConstants.COIN_ID_QUERY_KEY) coinId: Int): Single<JsonElement>

}