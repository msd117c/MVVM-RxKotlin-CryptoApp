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
        Assert.assertEquals("17.906.012", coinViewModel.getTotalSupply())

        testResults[0] = true
    }
}