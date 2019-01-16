package crypto.msd117c.com.cryptocurrency.di

import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.di.factory.ViewModelKey
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel : MainViewModel) : ViewModel
}