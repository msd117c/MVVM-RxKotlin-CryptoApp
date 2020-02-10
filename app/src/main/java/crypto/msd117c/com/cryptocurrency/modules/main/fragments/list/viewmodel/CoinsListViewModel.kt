package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import crypto.msd117c.com.cryptocurrency.base.viewmodel.BaseViewModel
import crypto.msd117c.com.cryptocurrency.domain.list.CoinsListUseCaseContract
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.viewmodel.CoinViewModel
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.ui.CoinsListUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CoinsListViewModel @Inject constructor(
    private val listCoinsListUseCaseContract: CoinsListUseCaseContract
) : BaseViewModel(),
    CoinsListViewModelContract {

    private val state = MutableLiveData<CoinsListState>()
    fun getState(): LiveData<CoinsListState> = state

    override fun getLatestCoins() {
        state.postValue(Loading)
        compositeDisposable.add(
            listCoinsListUseCaseContract
                .getLatestCoins()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    if (list.isEmpty()) {
                        state.postValue(
                            Error(
                                EmptyList
                            )
                        )
                    } else {
                        val listOfCoins = mutableListOf<CoinViewModel>()
                        list.forEach { coin -> listOfCoins.add(CoinViewModel(coin)) }
                        state.postValue(
                            Loaded(
                                CoinsListUiModel(
                                    listOfCoins
                                )
                            )
                        )
                    }
                }, { error ->
                    when (error) {
                        is UnknownHostException,
                        is ConnectException,
                        is SocketTimeoutException -> state.postValue(
                            Error(
                                NoConnection
                            )
                        )
                        else -> state.postValue(
                            Error(
                                UnknownError
                            )
                        )
                    }
                })
        )
    }
}

interface CoinsListViewModelContract {
    fun getLatestCoins()
}