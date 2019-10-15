package crypto.msd117c.com.cryptocurrency.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.coinmarketcap.com/v1/"
        const val END_POINT = "listings/latest?CMC_PRO_API_KEY=c76c7da6-9272-4cd3-8db7-4285836b112a"

        const val UNKNOWN_ERROR = -3
        const val NO_CONNECTION_ERROR = -2
        const val DATA_ERROR = -1
    }
}