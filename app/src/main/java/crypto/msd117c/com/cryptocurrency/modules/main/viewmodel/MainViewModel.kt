package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import crypto.msd117c.com.cryptocurrency.base.domain.model.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import kotlinx.coroutines.launch

class MainViewModel(
    private val coinsRepository: CoinsRepository
) : ViewModel() {

    val state = MutableLiveData<ViewModelStates>()
    val list = MutableLiveData<List<Datum>>()

    init {
        Log.d("VIEWMODEL", "initialized")
    }

    fun loadData(refresh: Boolean = false) {
        if (!refresh) {
            list.value?.let { itemsList ->
                list.postValue(itemsList)
                state.postValue(ViewModelStates.Loaded)
                return
            }
        }
        state.value = ViewModelStates.Loading
        viewModelScope.launch {
            try {
                val coins = coinsRepository.requestLatestCoins()
                list.postValue(coins.data)
                state.postValue(ViewModelStates.Loaded)
            } catch (e: NoConnectionException) {
                state.postValue(ViewModelStates.Error(NO_CONNECTION_ERROR))
            } catch (e: Exception) {
                state.postValue(ViewModelStates.Error(DATA_ERROR))
            }
        }
    }
}
