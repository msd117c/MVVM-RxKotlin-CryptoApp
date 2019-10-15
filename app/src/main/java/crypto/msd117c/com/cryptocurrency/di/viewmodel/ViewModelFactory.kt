package crypto.msd117c.com.cryptocurrency.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
import crypto.msd117c.com.cryptocurrency.util.NetworkManager
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val retrofitFactory: RetrofitFactory,
    private val networkManager: NetworkManager
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(retrofitFactory, networkManager) as T
    }

}