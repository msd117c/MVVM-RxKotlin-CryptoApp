package crypto.msd117c.com.cryptocurrency.domain.coins.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.utils.BaseTest
import crypto.msd117c.com.cryptocurrency.utils.RxImmediateSchedulerRule
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.*
import org.junit.runners.MethodSorters
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.IOException

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CoinsRepositoryTest : BaseTest(2) {

    companion object {
        @get:ClassRule
        @JvmStatic
        var schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var coinsNetwork: CoinsNetwork

    @InjectMocks
    lateinit var coinsRepository: CoinsRepository

    private val coinResponse = CoinResponse()
    private val coins = mutableListOf<Datum>()
    private lateinit var testObserver: TestObserver<CoinResponse>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        coins.add(Datum())
        coinResponse.data = coins

        `when`(coinsNetwork.retrieveLatestCoins()).thenReturn(Observable.just(coinResponse))
    }

    @Test
    fun test01ResponseOk() {
        testObserver = coinsRepository.requestLatestCoins().test()

        testObserver.assertValue { value ->
            value == coinResponse
        }

        testResults[0] = true
    }

    @Test
    fun test02ResponseKo() {
        `when`(coinsNetwork.retrieveLatestCoins()).thenReturn(Observable.error(IOException()))

        testObserver = coinsRepository.requestLatestCoins().test()

        testObserver.assertNoValues()
        testObserver.assertTerminated()

        testResults[1] = true
    }
}