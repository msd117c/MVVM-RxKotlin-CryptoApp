package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.lifecycle.ViewModel
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.util.extensions.comfy
import crypto.msd117c.com.cryptocurrency.util.extensions.normalize
import crypto.msd117c.com.cryptocurrency.util.extensions.toDate

class CoinViewModel(private val coin: Coin) : ViewModel() {


    fun getCoinTitle(): String {
        return if (coin.getName() != null) {
            coin.getName()!!
        } else {
            "N/A"
        }
    }

    fun getCoinUsd(): String {
        return if (coin.getPriceUsd() != null) {
            "${coin.getPriceUsd().normalize.comfy}$"
        } else {
            "N/A"
        }
    }

    fun getCoinPercentage(): String {
        return if (coin.getPercentChange1h() != null) {
            "${coin.getPercentChange1h().normalize}%"
        } else {
            "N/A"
        }
    }

    fun getSymbol(): String {
        return if (coin.getSymbol() != null) {
            coin.getSymbol()!!
        } else {
            "N/A"
        }
    }

    fun getRank(): String {
        return if (coin.getRank() != null) {
            coin.getRank()!!
        } else {
            "N/A"
        }
    }

    fun getMarketCap(): String {
        return if (coin.getMarketCapUsd() != null) {
            "${coin.getMarketCapUsd()?.normalize.comfy} BTC"
        } else {
            "N/A"
        }
    }

    fun getPriceBtc(): String {
        return if (coin.getPriceBtc() != null) {
            "${coin.getPriceBtc()?.normalize.comfy} BTC"
        } else {
            "N/A"
        }
    }

    fun getVolumeUsd24h(): String {
        return if (coin.get24hVolumeUsd() != null) {
            "${coin.get24hVolumeUsd()?.normalize.comfy}$"
        } else {
            "N/A"
        }
    }

    fun getAvailableSupply(): String {
        return if (coin.getAvailableSupply() != null) {
            coin.getAvailableSupply()?.normalize.comfy
        } else {
            "N/A"
        }
    }

    fun getTotalSupply(): String {
        return if (coin.getTotalSupply() != null) {
            coin.getTotalSupply()?.normalize.comfy
        } else {
            "N/A"
        }
    }

    fun getMaxSupply(): String {
        return if (coin.getMaxSupply() != null) {
            coin.getMaxSupply()?.normalize.comfy
        } else {
            "N/A"
        }
    }

    fun getPercentChange1h(): String {
        return if (coin.getPercentChange1h() != null) {
            "${coin.getPercentChange1h()?.normalize}% / 1 hour"
        } else {
            "N/A"
        }
    }

    fun getPercentChange24h(): String {
        return if (coin.getPercentChange24h() != null) {
            "${coin.getPercentChange24h()?.normalize}% / 24 hours"
        } else {
            "N/A"
        }
    }

    fun getPercentChange7d(): String {
        return if (coin.getPercentChange7d() != null) {
            "${coin.getPercentChange7d()?.normalize}% / 7 days"
        } else {
            "N/A"
        }
    }

    fun getLastUpdate(): String {
        return if (coin.getLastUpdated() != null) {
            coin.getLastUpdated()?.toInt().toDate
        } else {
            "N/A"
        }
    }

    fun isPositiveBalance(): Int {
        return if (coin.getPercentChange1h() != null && coin.getPercentChange24h() != null) {
            val change1h = coin.getPercentChange1h()!!.toDouble()
            val change24h = coin.getPercentChange24h()!!.toDouble()
            val result = change1h - change24h
            when {
                result > 0 -> 1
                result < 0 -> -1
                else -> 0
            }
        } else {
            0
        }
    }
}
