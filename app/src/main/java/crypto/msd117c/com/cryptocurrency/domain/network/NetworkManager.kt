package crypto.msd117c.com.cryptocurrency.domain.network

import android.content.Context
import android.net.ConnectivityManager

open class NetworkManager(private val context: Context) {

    open fun verifyAvailableNetwork(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}