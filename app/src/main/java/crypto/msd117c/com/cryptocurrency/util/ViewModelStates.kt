package crypto.msd117c.com.cryptocurrency.util

import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum

sealed class ViewModelStates {
    data class Loaded(val list: List<Datum>) : ViewModelStates()
    object Loading : ViewModelStates()
    data class Error(val type: Int) : ViewModelStates()
}
