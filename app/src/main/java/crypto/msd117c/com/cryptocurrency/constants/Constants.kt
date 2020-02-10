package crypto.msd117c.com.cryptocurrency.constants

object ApiConstants {
    const val BASE_URL = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/"
    const val BASE_TEST_URL = "http://localhost:8080/"
    const val LATEST_COINS_ENDPOINT = "listings/latest"
    const val COIN_DETAIL_ENDPOINT = "info"
    const val API_KEY = "ecb7308c-db5b-484b-81ea-840a1d205edf"
    const val API_KEY_ID = "CMC_PRO_API_KEY"
    const val COIN_ID_QUERY_KEY = "id"
}

object NavigationConstants {
    const val NAVIGATION_COIN_ID = "coinId"
}

object ItemViewType {
    const val URL_SECTION_VIEW_TYPE = 0
    const val URL_LINK_VIEW_TYPE = 1
}