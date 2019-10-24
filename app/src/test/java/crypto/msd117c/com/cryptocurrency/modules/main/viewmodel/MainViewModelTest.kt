package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import crypto.msd117c.com.cryptocurrency.domain.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.UNKNOWN_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import crypto.msd117c.com.cryptocurrency.utils.BaseTest
import crypto.msd117c.com.cryptocurrency.utils.RxImmediateSchedulerRule
import io.reactivex.Observable
import org.junit.*
import org.junit.runners.MethodSorters
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.IOException

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainViewModelTest : BaseTest(5) {

    companion object {
        @get:ClassRule
        @JvmStatic
        var schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var stateObserver: Observer<ViewModelStates>

    @Mock
    lateinit var listObserver: Observer<List<Datum>>

    @Mock
    lateinit var coinsRepository: CoinsRepository

    @InjectMocks
    lateinit var mainViewModel: MainViewModel

    private val coinResponse = CoinResponse()
    private val coins = mutableListOf<Datum>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        coins.add(Datum())
        coinResponse.data = coins

        mainViewModel.apply {
            state.observeForever(stateObserver)
            list.observeForever(listObserver)
        }

        `when`(coinsRepository.requestLatestCoins()).thenReturn(Observable.just(coinResponse))
    }

    @Test
    fun test01LoadDataWithNoCachedData() {
        mainViewModel.retrieveResponse()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Loaded)

        verify(listObserver).onChanged(coins)

        testResults[0] = true
    }

    @Test
    fun test02LoadDataWithCachedData() {
        mainViewModel.list.value = coins
        mainViewModel.retrieveResponse()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Loaded)

        verify(listObserver).onChanged(coins)

        testResults[1] = true
    }

    @Test
    fun test03LoadDataError() {
        coinResponse.data = null
        mainViewModel.retrieveResponse()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Error(DATA_ERROR))

        verify(listObserver).onChanged(mutableListOf())

        testResults[2] = true
    }

    @Test
    fun test04NoConnectionError() {
        `when`(coinsRepository.requestLatestCoins()).thenReturn(
            Observable.error(
                NoConnectionException()
            )
        )
        mainViewModel.retrieveResponse()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Error(NO_CONNECTION_ERROR))

        verify(listObserver).onChanged(mutableListOf())

        testResults[3] = true
    }

    @Test
    fun test05NoAPIKey() {
        `when`(coinsRepository.requestLatestCoins()).thenReturn(Observable.error(IOException()))
        mainViewModel.retrieveResponse()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Error(DATA_ERROR))

        verify(listObserver).onChanged(mutableListOf())

        testResults[4] = true
    }

}