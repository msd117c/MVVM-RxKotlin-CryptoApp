package crypto.msd117c.com.cryptocurrency.di.core

import android.app.Application
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import retrofit2.Retrofit

interface CoreComponent {
    val app: Application
    val networkManager: NetworkManager
    val retrofit: Retrofit
}