package crypto.msd117c.com.cryptocurrency.di

import android.app.Application

open class CryptoApp : Application() {

    open val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }
}