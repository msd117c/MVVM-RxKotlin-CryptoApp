package crypto.msd117c.com.cryptocurrency

import androidx.annotation.VisibleForTesting
import crypto.msd117c.com.cryptocurrency.di.RetrofitInstrumentedTestInstance
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit

@Module
@VisibleForTesting
class TestNetworkModule(private val mockWebServer: MockWebServer) {

    @Provides
    fun provideRetrofitClient(
        networkManager: NetworkManager
    ): Retrofit =
        RetrofitInstrumentedTestInstance().provideRetrofit(mockWebServer, networkManager)

}