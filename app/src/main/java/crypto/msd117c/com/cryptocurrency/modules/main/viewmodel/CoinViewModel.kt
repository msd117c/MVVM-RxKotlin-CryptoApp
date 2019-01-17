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
