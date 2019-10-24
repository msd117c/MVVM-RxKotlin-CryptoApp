package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.adapter

import com.google.gson.GsonBuilder
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.utils.BaseTest
import crypto.msd117c.com.cryptocurrency.utils.JsonGetter
import org.junit.Assert
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters

@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CoinViewModelTest : BaseTest(1) {

    private lateinit var coinViewModel: CoinViewModel

    @Before
    fun init() {
        val coin = GsonBuilder().create().fromJson(JsonGetter().getJSON("Bitcoin.json"), Datum::class.java)
        coinViewModel = CoinViewModel(coin)
    }

    @Test
    fun test01CheckFormat() {
        Assert.assertEquals("Bitcoin", coinViewModel.getCoinTitle())
        Assert.assertEquals("9.558,56 $", coinViewModel.getCoinUsd())
        Assert.assertEquals("-0,12%", coinViewModel.getCoinPercentage())
        Assert.assertEquals("BTC", coinViewModel.getSymbol())
        Assert.assertEquals("1", coinViewModel.getRank())
        Assert.assertEquals("171.155.540.318,87  BTC", coinViewModel.getMarketCap())
        Assert.assertEquals("9.558,56  BTC", coinViewModel.getPriceBtc())
        Assert.assertEquals("13.728.947.008,28 $", coinViewModel.getVolumeUsd24h())
        Assert.assertEquals("17.906.012", coinViewModel.getAvailableSupply())
        Assert.assertEquals("17.906.012", coinViewModel.getTotalSupply())
        Assert.assertEquals("21.000.000", coinViewModel.getMaxSupply())
        Assert.assertEquals("-0,12% / 1 hour", coinViewModel.getPercentChange1h())
        Assert.assertEquals("0,33% / 24 hours", coinViewModel.getPercentChange24h())
        Assert.assertEquals("-8% / 7 days", coinViewModel.getPercentChange7d())
        Assert.assertEquals("30-2019-08 18:51", coinViewModel.getLastUpdate())
        Assert.assertEquals(-1, coinViewModel.isPositiveBalance())

        testResults[0] = true
    }
}