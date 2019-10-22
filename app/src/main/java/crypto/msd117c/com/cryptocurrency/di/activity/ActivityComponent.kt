package crypto.msd117c.com.cryptocurrency.di.activity

import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainLifeCycle
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel

interface ActivityComponent {
    val mainViewModel: MainViewModel
    val mainLifeCycle: MainLifeCycle
}