package crypto.msd117c.com.cryptocurrency.utils

import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher

object RecyclerViewFormActions {

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }

}