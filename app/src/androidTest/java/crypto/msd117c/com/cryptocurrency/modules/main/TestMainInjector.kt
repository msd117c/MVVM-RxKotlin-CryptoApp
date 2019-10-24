package crypto.msd117c.com.cryptocurrency.modules.main

import androidx.test.platform.app.InstrumentationRegistry
import crypto.msd117c.com.cryptocurrency.TestNetworkModule
import crypto.msd117c.com.cryptocurrency.di.AppModule
import crypto.msd117c.com.cryptocurrency.di.test.CryptoAppTest
import crypto.msd117c.com.cryptocurrency.utils.MockWebServerDispatcher
import okhttp3.mockwebserver.MockWebServer

class TestMainInjector(
    private val testModule: TestMainModule,
    private val mockWebServer: MockWebServer
) {

    fun inject() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as CryptoAppTest

        mockWebServer.setDispatcher(MockWebServerDispatcher.RequestDispatcher(instrumentation.context))

        val component = DaggerMainTestApplicationComponent
            .builder()
            .appModule(AppModule())
            .testModule(testModule)
            .testNetworkModule(TestNetworkModule(mockWebServer))
            .application(app)
            .build()

        component.inject(app)
    }
}