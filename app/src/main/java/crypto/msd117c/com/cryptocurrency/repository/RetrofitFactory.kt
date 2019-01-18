package crypto.msd117c.com.cryptocurrency.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.util.RetrofitProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RetrofitFactory {
    private val disposable = CompositeDisposable()
    private val coinData = MutableLiveData<List<Coin>>()

    init {
        retrieveResponse()
    }

    private fun retrieveResponse() {
        disposable.add(RetrofitProvider.provideServiceInterface()
                .retrieveCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ response -> coinData.value = response})
    }

    fun getCoinData(): LiveData<List<Coin>> {
        return coinData
    }
}