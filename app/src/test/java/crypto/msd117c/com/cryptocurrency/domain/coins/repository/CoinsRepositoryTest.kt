package crypto.msd117c.com.cryptocurrency.domain.coins.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.network.CoinsNetwork
import crypto.msd117c.com.cryptocurrency.utils.BaseTest
import crypto.msd117c.com.cryptocurrency.utils.coroutines.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExperimentalCoroutinesApi
class CoinsRepositoryTest : BaseTest(1) {

    @get:Rule
    val mockitoRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var coinsNetwork: CoinsNetwork

    lateinit var coinsRepository: CoinsRepository

    private val coinResponse = CoinResponse()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        coinsRepository = CoinsRepository(coinsNetwork)

        runBlocking {
            `when`(coinsNetwork.retrieveLatestCoins()).thenReturn(coinResponse)
        }
    }

    @Test
    fun test01RequestLatestCoins() {
        runBlocking {
            Assert.assertEquals(coinsRepository.requestLatestCoins(), coinResponse)
        }

        testResults[0] = true
    }

}