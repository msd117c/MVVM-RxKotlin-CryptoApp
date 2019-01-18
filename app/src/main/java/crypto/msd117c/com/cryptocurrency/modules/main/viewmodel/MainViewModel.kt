package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.LOADED
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.LOADING
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val retrofitFactory = RetrofitFactory()
    private val disposable = CompositeDisposable()
    val state = MutableLiveData<Int>()
    private var list = ArrayList<Coin>()

    fun loadData() {
        state.value = LOADING
        disposable.add(retrofitFactory.retrieveResponse()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it != null && it.isNotEmpty()) {
                    list.addAll(it)
                    state.value = LOADED
                } else {
                    state.value = ERROR
                }
            }
        )
    }

    fun getData(): List<Coin> {
        return list
    }
}
