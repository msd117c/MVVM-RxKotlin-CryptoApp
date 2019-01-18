package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.util.normalize

class CoinViewModel(private val coin: Coin) : ViewModel() {

    fun getCoinTitle(): String {
        return coin.getName()!!
    }

    fun getCoinUsd(): String {
        return coin.getPriceUsd().normalize
    }

    fun getCoinPercentage(): String {
        return "${coin.getPercentChange1h().normalize}%"
    }

    fun getSymbol(): String {
        return coin.getSymbol()!!
    }

    fun getRank(): String {
        return coin.getRank()!!
    }

    fun getMarketCap(): String {
        return "MarketCap: ${coin.getMarketCapUsd()!!}USD"
    }

    fun getPriceBtc(): String {
        return "Price: ${coin.getPriceBtc().normalize}BTC"
    }

    fun getVolumeUsd24h(): String {
        return "Volume 24h: ${coin.get24hVolumeUsd().normalize}USD"
    }

    fun getAvailableSupply(): String {
        return "Available Supply: ${coin.getAvailableSupply().normalize}"
    }

    fun getTotalSupply(): String {
        return "Total Supply: ${coin.getTotalSupply().normalize}"
    }

    fun getMaxSupply(): String {
        return "Max Supply: ${coin.getMaxSupply().normalize}"
    }

    fun getPercentChange1h(): String {
        return "Percent change: ${coin.getPercentChange1h().normalize}% / 1 hour"
    }

    fun getPercentChange24h(): String {
        return "Percent change: ${coin.getPercentChange24h().normalize}% / 24 hours"
    }

    fun getPercentChange7d(): String {
        return "Percent change: ${coin.getPercentChange7d().normalize}% / 7 days"
    }

    fun getLastUpdate(): String {
        return "Last update: ${coin.getLastUpdated().normalize}"
    }

    fun isPositiveBalance(): Int {
        val change1h = coin.getPercentChange1h()!!.toDouble()
        val change24h = coin.getPercentChange24h()!!.toDouble()
        val result = change1h - change24h
        return when {
            result > 0 -> 1
            result < 0 -> -1
            else -> 0
        }
    }
}
