package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import crypto.msd117c.com.cryptocurrency.base.domain.model.NoConnectionException
import crypto.msd117c.com.cryptocurrency.di.viewmodel.CoroutineContextProvider
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val coinsRepository: CoinsRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    val state = MutableLiveData<ViewModelStates>()
    val list = MutableLiveData<List<Datum>>()

    fun loadData(refresh: Boolean = false) {
        state.value = ViewModelStates.Loading
        if (!refresh) {
            list.value?.let { itemsList ->
                list.postValue(itemsList)
                state.postValue(ViewModelStates.Loaded)
                return
            }
        }
        viewModelScope.launch {
            try {
                val coins = withContext(coroutineContextProvider.IO) {
                    coinsRepository.requestLatestCoins()
                }
                list.postValue(coins.data)
                if (coins.data.isEmpty()) {
                    state.postValue(ViewModelStates.Error(DATA_ERROR))
                } else {
                    state.postValue(ViewModelStates.Loaded)
                }
            } catch (e: NoConnectionException) {
                state.postValue(ViewModelStates.Error(NO_CONNECTION_ERROR))
            } catch (e: Exception) {
                state.postValue(ViewModelStates.Error(DATA_ERROR))
            }
        }
    }
}
