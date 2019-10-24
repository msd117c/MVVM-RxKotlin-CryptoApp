package crypto.msd117c.com.cryptocurrency.util

sealed class ViewModelStates {
    object Loaded : ViewModelStates()
    object Loading : ViewModelStates()
    data class Error(val type: Int) : ViewModelStates()
}
