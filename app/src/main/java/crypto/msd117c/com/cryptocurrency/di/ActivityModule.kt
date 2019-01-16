package crypto.msd117c.com.cryptocurrency.di

import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}