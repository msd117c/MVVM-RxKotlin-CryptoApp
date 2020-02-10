package crypto.msd117c.com.cryptocurrency.data.coins.repository

import com.google.gson.JsonElement
import crypto.msd117c.com.cryptocurrency.data.coins.repository.network.CoinsNetworkContract
import crypto.msd117c.com.cryptocurrency.data.model.list.Coin
import crypto.msd117c.com.cryptocurrency.data.model.list.CoinResponse
import io.reactivex.Single
import javax.inject.Inject

class CoinsRepository @Inject constructor(private val coinsNetworkContract: CoinsNetworkContract) :
    CoinsRepositoryContract {

    override fun requestLatestCoins(): Single<CoinResponse> =
        coinsNetworkContract.retrieveLatestCoins()

    override fun requestCoinDetail(coinId: Int): Single<JsonElement> =
        coinsNetworkContract.retrieveCoinDetail(coinId)

}

interface CoinsRepositoryContract {
    fun requestLatestCoins(): Single<CoinResponse>
    fun requestCoinDetail(coinId: Int): Single<JsonElement>
}