package crypto.msd117c.com.cryptocurrency.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val coinsRepository: CoinsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(coinsRepository) as T
    }

}