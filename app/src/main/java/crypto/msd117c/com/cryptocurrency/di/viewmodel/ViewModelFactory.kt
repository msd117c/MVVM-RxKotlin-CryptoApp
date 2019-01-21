package crypto.msd117c.com.cryptocurrency.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory

class ViewModelFactory(private val retrofitFactory: RetrofitFactory) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(retrofitFactory) as T
    }

}