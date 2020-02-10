package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.GsonBuilder
import crypto.msd117c.com.cryptocurrency.data.model.list.CoinResponse
import crypto.msd117c.com.cryptocurrency.domain.list.CoinsListUseCaseContract
import crypto.msd117c.com.cryptocurrency.utils.JsonGetter
import crypto.msd117c.com.cryptocurrency.utils.RxImmediateSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.net.ConnectException

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4::class)
class CoinsListViewModelTest : CoinsListViewModelTestContract {

    @get:Rule
    val rxRule = RxImmediateSchedulerRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var coinsListUseCaseContract: CoinsListUseCaseContract

    @InjectMocks
    lateinit var coinsListViewModel: CoinsListViewModel

    private val coinsResponse =
        GsonBuilder().create().fromJson(
            JsonGetter.getJSON("cryptocurrency/listings/latest/response.json"),
            CoinResponse::class.java
        )

    @Mock
    lateinit var testObserver: Observer<CoinsListState>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        coinsListViewModel.getState().observeForever(testObserver)
    }

    @Test
    override fun testCase01GetLatestCoins() {
        `when`(coinsListUseCaseContract.getLatestCoins()).thenReturn(Single.just(coinsResponse.data))

        coinsListViewModel.getLatestCoins()

        verify(testObserver).onChanged(Loading)
        assert((coinsListViewModel.getState().value as Loaded).mainUiModel.listOfCoins.size == coinsResponse.data.size)
    }

    @Test
    override fun testCase02GetEmptyList() {
        `when`(coinsListUseCaseContract.getLatestCoins()).thenReturn(Single.just(mutableListOf()))

        coinsListViewModel.getLatestCoins()

        verify(testObserver).onChanged(Loading)
        verify(testObserver).onChanged(Error(EmptyList))
    }

    @Test
    override fun testCase03ConnectionError() {
        `when`(coinsListUseCaseContract.getLatestCoins()).thenReturn(Single.error(ConnectException()))

        coinsListViewModel.getLatestCoins()

        verify(testObserver).onChanged(Loading)
        verify(testObserver).onChanged(Error(NoConnection))
    }

    @Test
    override fun testCase04UnknownError() {
        `when`(coinsListUseCaseContract.getLatestCoins()).thenReturn(Single.error(IOException()))

        coinsListViewModel.getLatestCoins()

        verify(testObserver).onChanged(Loading)
        verify(testObserver).onChanged(Error(UnknownError))
    }

}

interface CoinsListViewModelTestContract {
    fun testCase01GetLatestCoins()
    fun testCase02GetEmptyList()
    fun testCase03ConnectionError()
    fun testCase04UnknownError()
}