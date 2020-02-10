package crypto.msd117c.com.cryptocurrency.data.coins.repository.network

import com.google.gson.JsonElement
import crypto.msd117c.com.cryptocurrency.data.model.list.Coin
import crypto.msd117c.com.cryptocurrency.data.model.list.CoinResponse
import io.reactivex.Single
import javax.inject.Inject

class CoinsNetwork @Inject constructor(private val coinsService: CoinsService): CoinsNetworkContract {

    override fun retrieveLatestCoins(): Single<CoinResponse> =
        coinsService.retrieveCoins()

    override fun retrieveCoinDetail(coinId: Int): Single<JsonElement> =
        coinsService.retrieveCoinDetail(coinId)

}

interface CoinsNetworkContract {
    fun retrieveLatestCoins(): Single<CoinResponse>
    fun retrieveCoinDetail(coinId: Int): Single<JsonElement>
}