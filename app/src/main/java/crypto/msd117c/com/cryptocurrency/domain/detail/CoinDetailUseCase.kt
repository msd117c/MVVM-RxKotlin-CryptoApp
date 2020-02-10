package crypto.msd117c.com.cryptocurrency.domain.detail

import com.google.gson.GsonBuilder
import crypto.msd117c.com.cryptocurrency.data.coins.repository.CoinsRepositoryContract
import crypto.msd117c.com.cryptocurrency.data.model.detail.CoinDetail
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinDetailUseCase @Inject constructor(
    private val coinsRepositoryContract: CoinsRepositoryContract
) : CoinDetailUseCaseContract {

    override fun getCoinDetail(coinId: Int): Single<CoinDetail> =
        coinsRepositoryContract.requestCoinDetail(coinId)
            .flatMap { jsonElement ->
                val coinDetail = GsonBuilder().create().fromJson(
                    jsonElement.asJsonObject.get("data")
                        .asJsonObject.get(coinId.toString()), CoinDetail::class.java
                )
                Single.just(coinDetail)
            }
            .subscribeOn(Schedulers.io())

}

interface CoinDetailUseCaseContract {
    fun getCoinDetail(coinId: Int): Single<CoinDetail>
}