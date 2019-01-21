package crypto.msd117c.com.cryptocurrency

import android.arch.core.executor.testing.InstantTaskExecutorRule
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainActivity
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.timeout
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var mainActivity: MainActivity

    @Mock
    lateinit var retrofitFactory: RetrofitFactory

    @get:Rule
    val mockitoRule = InstantTaskExecutorRule()

    private val listOfCoins = ArrayList<Coin>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainViewModel = MainViewModel(retrofitFactory)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testRetrieveData() {
        /* val observer = mock(Observer::class.java) as Observer<ViewModelStates>
         this.mainViewModel.state.observeForever(observer)
 */
        this.mainViewModel.loadData(false)
        assert(this.mainViewModel.state.value == ViewModelStates.Error(DATA_ERROR))

        listOfCoins.add(Coin())
        `when`(retrofitFactory.retrieveResponse()).thenReturn(Observable.just(listOfCoins))
        this.mainViewModel.loadData(true)
        assert(this.mainViewModel.state.value == ViewModelStates.Loading)
        timeout(3000)
        assert(this.mainViewModel.state.value == ViewModelStates.Loaded(listOfCoins))
    }
}