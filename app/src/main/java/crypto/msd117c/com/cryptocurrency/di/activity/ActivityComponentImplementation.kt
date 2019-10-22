package crypto.msd117c.com.cryptocurrency.di.activity

import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelComponent
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel

class ActivityComponentImplementation(private val viewModelComponent: ViewModelComponent) :
    ActivityComponent, ViewModelComponent by viewModelComponent {
    override val mainViewModel: MainViewModel
        get() = viewModelFactory.create(MainViewModel::class.java)
}