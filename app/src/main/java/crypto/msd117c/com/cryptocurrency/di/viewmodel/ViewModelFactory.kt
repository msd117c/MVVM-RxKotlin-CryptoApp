package crypto.msd117c.com.cryptocurrency.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory<V : ViewModel> @Inject constructor(private val viewModel: Lazy<V>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.get() as T

}