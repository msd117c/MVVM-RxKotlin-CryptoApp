package crypto.msd117c.com.cryptocurrency.di.test

import com.google.gson.GsonBuilder
import crypto.msd117c.com.cryptocurrency.domain.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import crypto.msd117c.com.cryptocurrency.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

class RetrofitInstrumentedTestInstance {

    fun provideRetrofit(mockWebServer: MockWebServer, networkManager: NetworkManager): Retrofit {
        val client = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logging)

        client.addInterceptor { chain ->
            var request = chain.request()

            val url =
                request.url().newBuilder()
                    .addQueryParameter(Constants.API_KEY_ID, Constants.API_KEY)
                    .build()
            request = request.newBuilder().url(url).build()

            if (!networkManager.verifyAvailableNetwork()) {
                throw NoConnectionException()
            }

            chain.proceed(request)
        }

        // Set timeout
        client.readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .client(client.build())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .excludeFieldsWithModifiers(
                            Modifier.FINAL,
                            Modifier.TRANSIENT,
                            Modifier.STATIC
                        )
                        .setLenient()
                        .create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}