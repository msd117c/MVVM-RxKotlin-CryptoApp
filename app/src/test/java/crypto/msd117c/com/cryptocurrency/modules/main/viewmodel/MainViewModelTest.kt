package crypto.msd117c.com.cryptocurrency.modules.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import crypto.msd117c.com.cryptocurrency.base.domain.model.NoConnectionException
import crypto.msd117c.com.cryptocurrency.domain.coins.model.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.domain.coins.repository.CoinsRepository
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import crypto.msd117c.com.cryptocurrency.utils.BaseTest
import crypto.msd117c.com.cryptocurrency.utils.coroutines.TestContextProvider
import crypto.msd117c.com.cryptocurrency.utils.coroutines.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Body
import java.io.IOException

@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTest(5) {

    @get:Rule
    val mockitoRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var coinsRepository: CoinsRepository

    @Mock
    lateinit var stateObserver: Observer<ViewModelStates>

    @Mock
    lateinit var listObserver: Observer<List<Datum>>

    lateinit var mainViewModel: MainViewModel

    private val coinResponse = CoinResponse()
    private val listOfCoins = ArrayList<Datum>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mainViewModel = MainViewModel(
            coinsRepository,
            TestContextProvider()
        ).apply {
            state.observeForever(stateObserver)
            list.observeForever(listObserver)
        }

        runBlocking {
            `when`(coinsRepository.requestLatestCoins()).thenReturn(coinResponse)
        }
    }

    @Test
    fun test01LoadDataWithNoCachedData() {
        listOfCoins.add(Datum())
        coinResponse.data = listOfCoins

        mainViewModel.loadData()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Loaded)

        verify(listObserver).onChanged(listOfCoins)

        testResults[0] = true
    }

    @Test
    fun test02LoadDataWithCachedData() {
        listOfCoins.add(Datum())
        mainViewModel.list.value = listOfCoins
        mainViewModel.loadData()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Loaded)

        testResults[1] = true
    }

    @Test
    fun test03LoadDataErrorResponse() {
        listOfCoins.clear()
        coinResponse.data = listOfCoins

        mainViewModel.loadData()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Error(DATA_ERROR))

        verify(listObserver).onChanged(listOfCoins)

        testResults[2] = true
    }

    @Test
    fun test04NoConnection() {
        runBlocking {
            `when`(coinsRepository.requestLatestCoins()).thenThrow(NoConnectionException())
        }

        mainViewModel.loadData()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Error(NO_CONNECTION_ERROR))

        verify(listObserver).onChanged(mutableListOf())

        testResults[3] = true
    }

    @Test
    fun test05NoApiKey() {
        runBlocking {
            `when`(coinsRepository.requestLatestCoins()).thenThrow(IOException())
        }

        mainViewModel.loadData()

        verify(stateObserver).onChanged(ViewModelStates.Loading)
        verify(stateObserver).onChanged(ViewModelStates.Error(DATA_ERROR))

        verify(listObserver).onChanged(mutableListOf())

        testResults[4] = true
    }
}