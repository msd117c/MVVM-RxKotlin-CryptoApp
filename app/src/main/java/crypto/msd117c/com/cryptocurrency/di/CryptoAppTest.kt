package crypto.msd117c.com.cryptocurrency.di

class CryptoAppTest : CryptoApp() {
    companion object {
        var inTests = false
    }

    init {
        inTests = true
    }
}