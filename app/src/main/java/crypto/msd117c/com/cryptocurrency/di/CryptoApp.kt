package crypto.msd117c.com.cryptocurrency.di

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class CryptoApp : Application(), HasActivityInjector, BaseApplication {

    override fun getContext(): Context = applicationContext

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        // initialize Dagger
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    // this is required to setup Dagger2 for Activity
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}