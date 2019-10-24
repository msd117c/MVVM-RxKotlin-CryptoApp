package crypto.msd117c.com.cryptocurrency.di.test

import crypto.msd117c.com.cryptocurrency.di.CryptoApp

class CryptoAppTest : CryptoApp() {
    companion object {
        var inTests = false
    }

    init {
        inTests = true
    }
}