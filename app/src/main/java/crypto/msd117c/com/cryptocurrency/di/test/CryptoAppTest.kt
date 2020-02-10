package crypto.msd117c.com.cryptocurrency.di.test

import crypto.msd117c.com.cryptocurrency.di.CryptoApp

class CryptoAppTest : CryptoApp() {

    override val appComponent: TestAppComponent by lazy {
        DaggerTestAppComponent.create()
    }

}