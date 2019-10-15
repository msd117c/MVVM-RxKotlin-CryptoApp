package crypto.msd117c.com.cryptocurrency.util

import crypto.msd117c.com.cryptocurrency.domain.coins.model.Coin

sealed class ViewModelStates {
    data class Loaded(val list: List<Coin>) : ViewModelStates()
    object Loading : ViewModelStates()
    data class Error(val type: Int) : ViewModelStates()
}
