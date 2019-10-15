package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.UNKNOWN_ERROR
import crypto.msd117c.com.cryptocurrency.util.NetworkManager
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val networkManager: NetworkManager
) : ViewModel() {
    private val disposable = CompositeDisposable()
    val state = MutableLiveData<ViewModelStates>()
    private var list = ArrayList<Datum>()

    fun loadData(apiKey: String) {
        val connection = networkManager.verifyAvailableNetwork()
        if (!connection) {
            if (list.isNotEmpty()) {
                state.postValue(ViewModelStates.Loaded(list))
                return
            } else {
                state.postValue(ViewModelStates.Error(DATA_ERROR))
                return
            }
        } else {
            retrieveResponse(apiKey)
        }
    }

    fun retrieveResponse(apiKey: String) {
        state.value = ViewModelStates.Loading
        val request = HashMap<String, String>()
        request["CMC_PRO_API_KEY"] = apiKey
        disposable.add(
            coinsRepository.requestLatestCoins(request)
                .flatMap { coinResponse ->
                    val listOfCoins = coinResponse.data
                    if (listOfCoins.isNotEmpty()) {
                        list.clear()
                        list.addAll(listOfCoins)
                        state.postValue(ViewModelStates.Loaded(listOfCoins))
                    } else {
                        state.postValue(ViewModelStates.Error(DATA_ERROR))
                    }
                    Observable.just(listOfCoins)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Any>() {
                    override fun onComplete() {}

                    override fun onNext(t: Any) {}

                    override fun onError(e: Throwable) {
                        state.postValue(ViewModelStates.Error(UNKNOWN_ERROR))
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
