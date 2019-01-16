package crypto.msd117c.com.cryptocurrency.repository

import android.arch.lifecycle.LiveData
import crypto.msd117c.com.cryptocurrency.model.Response
import crypto.msd117c.com.cryptocurrency.util.RetrofitProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RetrofitFactory : LiveData<List<Response>>() {
    private val disposable = CompositeDisposable()

    fun retrieveResponse() {
        disposable.add(RetrofitProvider.provideServiceInterface()
                .retrieveCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ response -> postValue(response)})
    }
}