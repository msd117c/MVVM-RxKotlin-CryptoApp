package crypto.msd117c.com.cryptocurrency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainActivity
import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainLifeCycle
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private val listOfCoins = ArrayList<Coin>()
    private var numOfTimes = 1

    @Mock
    lateinit var mainActivity: MainActivity

    @Mock
    lateinit var mainLifeCycle: MainLifeCycle

    @Mock
    lateinit var retrofitFactory: RetrofitFactory

    @get:Rule
    val mockitoRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainViewModel = MainViewModel(this.retrofitFactory)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testRetrieveData() {
        loadDataWithoutConnectionAndNoCachedData()
        loadDataWithConnectionAndNoCachedData()
        loadDataWithoutConnectionAndCachedData()
        loadDataWithConnectionAndNoDataIsReturned()
    }

    private fun loadDataWithoutConnectionAndNoCachedData() {
        this.mainViewModel.loadData()

        assert(this.mainViewModel.state.value == ViewModelStates.Error(DATA_ERROR))
        verifyZeroInteractions(this.mainActivity)
        verifyZeroInteractions(this.mainLifeCycle)
        verifyZeroInteractions(this.retrofitFactory)
    }

    private fun loadDataWithConnectionAndNoCachedData() {
        this.listOfCoins.add(Coin())

        `when`(retrofitFactory.retrieveResponse()).thenReturn(Observable.just(this.listOfCoins))
        this.mainViewModel.loadData()

        assert(this.mainViewModel.state.value == ViewModelStates.Loading)
        verifyZeroInteractions(this.mainActivity)
        verifyZeroInteractions(this.mainLifeCycle)
        verify(retrofitFactory, times(numOfTimes++)).retrieveResponse()

        Thread.sleep(1000)

        assert(this.mainViewModel.state.value == ViewModelStates.Loaded(this.listOfCoins))
        verifyZeroInteractions(this.mainActivity)
        verifyZeroInteractions(this.mainLifeCycle)

    }

    private fun loadDataWithoutConnectionAndCachedData() {
        this.mainViewModel.loadData()

        assert(this.mainViewModel.state.value == ViewModelStates.Loaded(this.listOfCoins))
        verifyZeroInteractions(this.mainActivity)
        verifyZeroInteractions(this.mainLifeCycle)
        verifyZeroInteractions(this.retrofitFactory)
    }

    private fun loadDataWithConnectionAndNoDataIsReturned() {
        this.listOfCoins.clear()
        `when`(this.retrofitFactory.retrieveResponse()).thenReturn(Observable.just(this.listOfCoins))

        this.mainViewModel.loadData()

        assert(this.mainViewModel.state.value == ViewModelStates.Loading)
        verifyZeroInteractions(this.mainActivity)
        verifyZeroInteractions(this.mainLifeCycle)
        verify(retrofitFactory, times(numOfTimes++)).retrieveResponse()

        Thread.sleep(1000)

        assert(this.mainViewModel.state.value == ViewModelStates.Error(DATA_ERROR))
        verifyZeroInteractions(this.mainActivity)
        verifyZeroInteractions(this.mainLifeCycle)
    }
}