package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val retrofitFactory = RetrofitFactory()

    fun reloadData(): RetrofitFactory {
        retrofitFactory.retrieveResponse()
        return retrofitFactory
    }
}
