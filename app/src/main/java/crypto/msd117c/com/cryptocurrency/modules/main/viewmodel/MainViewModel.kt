package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.domain.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.UNKNOWN_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val state = MutableLiveData<ViewModelStates>()
    val list = MutableLiveData<List<Datum>>()

    fun retrieveResponse(refresh: Boolean = false) {
        state.value = ViewModelStates.Loading
        if (!refresh) {
            list.value?.let {
                state.postValue(ViewModelStates.Loaded)
                return
            }
        }
        disposable.add(
            coinsRepository.requestLatestCoins()
                .flatMap { coinResponse ->
                    val listOfCoins = coinResponse.data
                    if (listOfCoins != null) {
                        list.postValue(listOfCoins)
                        if (listOfCoins.isNotEmpty()) {
                            state.postValue(ViewModelStates.Loaded)
                        } else {
                            state.postValue(ViewModelStates.Error(DATA_ERROR))
                        }
                    } else {
                        list.postValue(mutableListOf())
                        state.postValue(ViewModelStates.Error(DATA_ERROR))
                    }
                    Observable.just(coinResponse)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableObserver<Any>() {
                        override fun onComplete() {}

                        override fun onNext(t: Any) {}

                        override fun onError(e: Throwable) {
                            list.postValue(mutableListOf())
                            state.postValue(
                                ViewModelStates.Error(
                                    when (e) {
                                        is NoConnectionException -> NO_CONNECTION_ERROR
                                        is IOException -> DATA_ERROR
                                        else -> UNKNOWN_ERROR
                                    }
                                )
                            )
                        }

                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
