package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val retrofitFactory: RetrofitFactory) : ViewModel() {
    private val disposable = CompositeDisposable()
    val state = MutableLiveData<ViewModelStates>()
    private var list = ArrayList<Coin>()

    fun loadData(connection: Boolean) {
        if (!connection) {
            if (list.isNotEmpty()) {
                state.value = ViewModelStates.Loaded(list)
                return
            } else {
                state.value = ViewModelStates.Error(DATA_ERROR)
                return
            }
        } else {
            retrieveResponse()
        }
    }

    fun retrieveResponse() {
        state.value = ViewModelStates.Loading
        disposable.add(
            retrofitFactory.retrieveResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null && it.isNotEmpty()) {
                        list.clear()
                        list.addAll(it)
                        state.value = ViewModelStates.Loaded(list)
                    } else {
                        state.value = ViewModelStates.Error(DATA_ERROR)
                    }
                }, { state.value = ViewModelStates.Error(NO_CONNECTION_ERROR) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
