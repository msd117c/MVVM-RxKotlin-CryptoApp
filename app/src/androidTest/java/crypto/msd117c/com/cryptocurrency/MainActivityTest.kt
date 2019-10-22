package crypto.msd117c.com.cryptocurrency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import crypto.msd117c.com.cryptocurrency.modules.main.ui.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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

    private lateinit var mainActivity: MainActivity

    @get:Rule
    var rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @get:Rule
    val mockitoRule = InstantTaskExecutorRule()

    @Test
    @SmallTest
    fun useAppContext() {
        mainActivity = rule.activity
        assertNotNull(mainActivity.getBinding())

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("crypto.msd117c.com.cryptocurrency", appContext.packageName)
    }
}
