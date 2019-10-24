package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import crypto.msd117c.com.cryptocurrency.modules.main.TestMainInjector
import crypto.msd117c.com.cryptocurrency.modules.main.TestMainModule
import crypto.msd117c.com.cryptocurrency.utils.MockWebServerDispatcher
import crypto.msd117c.com.cryptocurrency.utils.RecyclerViewMatcher
import crypto.msd117c.com.cryptocurrency.utils.WaitingForViewModelStateResource
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var context: Context
    private lateinit var resourcesContext: Context

    private val networkManager = mockk<NetworkManager>(relaxed = true)

    private lateinit var idlingResource: IdlingResource

    private val mockWebServer = MockWebServer()

    @Before
    fun init() {
        context = InstrumentationRegistry.getInstrumentation().context
        resourcesContext = InstrumentationRegistry.getInstrumentation().targetContext
        mockWebServer.start(8080)
        TestMainInjector(
            TestMainModule(
                networkManager
            ),
            mockWebServer
        )
            .inject()

        every { networkManager.verifyAvailableNetwork() } returns true
    }

    @Test
    fun test01LoadData() {
        activityRule.launchActivity(Intent())

        idlingResource = WaitingForViewModelStateResource(
            activityRule.activity.viewModel.list,
            activityRule.activity
        )
        IdlingRegistry.getInstance().register(idlingResource)

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.list)

        onView(RecyclerViewMatcher(recyclerView).atPosition(0)).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun test02NoConnection() {
        every { networkManager.verifyAvailableNetwork() } returns false

        activityRule.launchActivity(Intent())

        idlingResource = WaitingForViewModelStateResource(
            activityRule.activity.viewModel.state,
            activityRule.activity
        )
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withText(resourcesContext.getString(R.string.no_connection))).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun test03DataError() {
        mockWebServer.setDispatcher(MockWebServerDispatcher.RequestInvalidDispatcher(context))
        activityRule.launchActivity(Intent())

        idlingResource = WaitingForViewModelStateResource(
            activityRule.activity.viewModel.state,
            activityRule.activity
        )
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withText(resourcesContext.getString(R.string.data_error))).check(matches(isDisplayed()))
    }

    @Test
    fun test04MalformedJsonError() {
        mockWebServer.setDispatcher(MockWebServerDispatcher.RequestMalformedDispatcher(context))
        activityRule.launchActivity(Intent())

        idlingResource = WaitingForViewModelStateResource(
            activityRule.activity.viewModel.state,
            activityRule.activity
        )
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withText(resourcesContext.getString(R.string.data_error))).check(matches(isDisplayed()))
    }

    @After
    fun release() {
        activityRule.finishActivity()
        if (::idlingResource.isInitialized) {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
        mockWebServer.shutdown()
    }
}
