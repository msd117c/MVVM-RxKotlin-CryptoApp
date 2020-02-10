package crypto.msd117c.com.cryptocurrency.domain.list

import crypto.msd117c.com.cryptocurrency.data.coins.repository.CoinsRepositoryContract
import crypto.msd117c.com.cryptocurrency.data.model.list.Coin
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinsListUseCase @Inject constructor(
    private val coinsRepositoryContract: CoinsRepositoryContract
) : CoinsListUseCaseContract {

    override fun getLatestCoins(): Single<List<Coin>> =
        coinsRepositoryContract.requestLatestCoins()
            .flatMap { response ->
                Single.just(response.data)
            }
            .subscribeOn(Schedulers.io())

}

interface CoinsListUseCaseContract {
    fun getLatestCoins(): Single<List<Coin>>
}