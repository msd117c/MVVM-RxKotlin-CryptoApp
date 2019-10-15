package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.util.extensions.comfy
import crypto.msd117c.com.cryptocurrency.util.extensions.normalize
import crypto.msd117c.com.cryptocurrency.util.extensions.toDate

class CoinViewModel(private val coin: Coin) : ViewModel() {

    fun getCoinTitle(): String = coin.name ?: "N/A"

    fun getCoinUsd(): String = "${coin.priceUsd.normalize.comfy}$"

    fun getCoinPercentage(): String = "${coin.percentChange1h.normalize}%"

    fun getSymbol(): String = coin.symbol ?: "N/A"

    fun getRank(): String = coin.rank ?: "N/A"

    fun getMarketCap(): String = "${coin.marketCapUsd.normalize.comfy} BTC"

    fun getPriceBtc(): String = "${coin.priceBtc.normalize.comfy} BTC"

    fun getVolumeUsd24h(): String = "${coin._24hVolumeUsd.normalize.comfy}$"

    fun getAvailableSupply(): String = coin.availableSupply.normalize.comfy

    fun getTotalSupply(): String = coin.totalSupply.normalize.comfy

    fun getMaxSupply(): String = coin.maxSupply.normalize.comfy

    fun getPercentChange1h(): String = "${coin.percentChange1h.normalize}% / 1 hour"

    fun getPercentChange24h(): String = "${coin.percentChange24h.normalize}% / 24 hours"

    fun getPercentChange7d(): String = "${coin.percentChange7d.normalize}% / 7 days"

    fun getLastUpdate(): String = coin.lastUpdated?.toInt().toDate

    fun isPositiveBalance(): Int {
        coin.percentChange1h?.let { change1h ->
            coin.percentChange24h?.let { change24h ->
                return try {
                    val result = change1h.toDouble() - change24h.toDouble()
                    when {
                        result > 0 -> 1
                        result < 0 -> -1
                        else -> 0
                    }
                } catch (e: Exception) {
                    0
                }
            }
            return 0
        }
        return 0
    }
}
