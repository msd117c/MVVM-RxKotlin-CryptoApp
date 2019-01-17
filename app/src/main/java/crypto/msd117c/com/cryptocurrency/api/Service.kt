package crypto.msd117c.com.cryptocurrency.api

import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.END_POINT
import io.reactivex.Observable
import retrofit2.http.GET

interface Service {
    @GET(END_POINT)
    fun retrieveCoins(): Observable<List<Coin>>
}