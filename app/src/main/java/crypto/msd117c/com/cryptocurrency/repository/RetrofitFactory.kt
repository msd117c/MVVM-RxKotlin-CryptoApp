package crypto.msd117c.com.cryptocurrency.repository

import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.util.RetrofitProvider
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class RetrofitFactory {
    fun retrieveResponse(): Observable<List<Coin>> {
        return RetrofitProvider.provideServiceInterface()
            .retrieveCoins()
    }
}