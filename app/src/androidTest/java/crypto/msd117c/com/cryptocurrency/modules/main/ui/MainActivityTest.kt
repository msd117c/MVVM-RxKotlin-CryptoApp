package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.domain.network.NetworkManager
import crypto.msd117c.com.cryptocurrency.modules.main.TestMainInjector
import crypto.msd117c.com.cryptocurrency.modules.main.TestMainModule
import crypto.msd117c.com.cryptocurrency.utils.RecyclerViewMatcher
import crypto.msd117c.com.cryptocurrency.utils.WaitingForViewModelStateResource
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var context: Context

    private val networkManager = mockk<NetworkManager>(relaxed = true)

    private lateinit var idlingResource: IdlingResource

    private val mockWebServer = MockWebServer()

    @Before
    fun init() {
        context = InstrumentationRegistry.getInstrumentation().context
        TestMainInjector(
            TestMainModule(
                networkManager
            ),
            mockWebServer
        )
            .inject()

        mockWebServer.start(8080)
    }

    fun test01LoadData() {
        activityRule.launchActivity(Intent())

        idlingResource = WaitingForViewModelStateResource(
            activityRule.activity.viewModel.state,
            activityRule.activity
        )
        IdlingRegistry.getInstance().register(idlingResource)

        onView(RecyclerViewMatcher(R.id.list).atPosition(0)).check(matches(isDisplayed()))
    }
}
