package crypto.msd117c.com.cryptocurrency.di.core

import android.app.Application
import crypto.msd117c.com.cryptocurrency.BuildConfig
import crypto.msd117c.com.cryptocurrency.base.domain.model.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import crypto.msd117c.com.cryptocurrency.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoreComponentImplementation(override val app: Application) :
    CoreComponent {

    override var networkManager = NetworkManager(app)

    override val retrofit: Retrofit by lazy {
        if (Constants.inTest) {
            Retrofit.Builder()
                .baseUrl(Constants.TEST_URL)
                .client(provideHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } else {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(provideHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

    private fun provideHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        client.addInterceptor { chain ->
            var request = chain.request()

            val url = request.url().newBuilder().addQueryParameter(
                Constants.API_QUERY_ID,
                Constants.API_KEY
            ).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
            if (networkManager.verifyAvailableNetwork()) {
                chain.proceed(request)
            } else {
                throw NoConnectionException()
            }
        }

        // Set timeout
        client.readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        return client.build()
    }

}