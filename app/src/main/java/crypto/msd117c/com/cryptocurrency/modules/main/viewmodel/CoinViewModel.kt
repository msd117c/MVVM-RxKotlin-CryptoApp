package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.util.extensions.comfy
import crypto.msd117c.com.cryptocurrency.util.extensions.normalize
import crypto.msd117c.com.cryptocurrency.util.extensions.toDate

class CoinViewModel(private val coin: Datum) : ViewModel() {

    fun getCoinTitle(): String = coin.name ?: "N/A"

    fun getCoinUsd(): String = "${coin.quote.usd.price?.toString().normalize.comfy}$"

    fun getCoinPercentage(): String = "${coin.quote.usd.percentChange1h?.toString().normalize}%"

    fun getSymbol(): String = coin.symbol ?: "N/A"

    fun getRank(): String = coin.cmcRank?.toString() ?: "N/A"

    fun getMarketCap(): String = "${coin.quote.usd.marketCap?.toString().normalize.comfy} BTC"

    fun getPriceBtc(): String = "${coin.quote.usd.price?.toString().normalize.comfy} BTC"

    fun getVolumeUsd24h(): String = "${coin.quote.usd.volume24h?.toString().normalize.comfy}$"

    fun getAvailableSupply(): String = coin.circulatingSupply?.toString().normalize.comfy

    fun getTotalSupply(): String = coin.totalSupply?.toString().normalize.comfy

    fun getMaxSupply(): String = coin.maxSupply?.toString().normalize.comfy

    fun getPercentChange1h(): String = "${coin.quote.usd.percentChange1h?.toString().normalize}% / 1 hour"

    fun getPercentChange24h(): String = "${coin.quote.usd.percentChange24h?.toString().normalize}% / 24 hours"

    fun getPercentChange7d(): String = "${coin.quote.usd.percentChange7d?.toString().normalize}% / 7 days"

    fun getLastUpdate(): String = coin.lastUpdated.toDate

    fun isPositiveBalance(): Int {
        coin.quote.usd.percentChange1h?.let { change1h ->
            coin.quote.usd.percentChange24h?.let { change24h ->
                return try {
                    val result = change1h - change24h
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
