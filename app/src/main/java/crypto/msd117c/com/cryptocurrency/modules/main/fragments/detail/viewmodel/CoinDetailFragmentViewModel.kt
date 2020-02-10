package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import crypto.msd117c.com.cryptocurrency.base.viewmodel.BaseViewModel
import crypto.msd117c.com.cryptocurrency.domain.detail.CoinDetailUseCaseContract
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.ui.CoinDetailUiModel
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.viewmodel.CoinViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CoinDetailFragmentViewModel @Inject constructor(
    private val coinDetailUseCaseContract: CoinDetailUseCaseContract
) : BaseViewModel(), CoinDetailViewModelContract {

    private val state = MutableLiveData<CoinDetailState>()
    fun getState(): LiveData<CoinDetailState> = state

    override fun getCoinDetail(coinId: Int) {
        state.postValue(Loading)
        compositeDisposable.add(
            coinDetailUseCaseContract.getCoinDetail(coinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ coin ->
                    if (coin == null) {
                        state.postValue(Error(NoData))
                    } else {
                        state.postValue(Loaded(CoinDetailUiModel(CoinDetailViewModel(coin))))
                    }
                }, { error ->
                    when (error) {
                        is UnknownHostException,
                        is ConnectException,
                        is SocketTimeoutException -> state.postValue(Error(NoConnection))
                        else -> state.postValue(Error(UnknownError))
                    }
                })
        )
    }

}

interface CoinDetailViewModelContract {
    fun getCoinDetail(coinId: Int)
}