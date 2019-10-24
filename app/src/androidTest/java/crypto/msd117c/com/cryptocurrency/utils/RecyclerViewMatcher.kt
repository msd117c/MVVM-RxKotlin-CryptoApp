package crypto.msd117c.com.cryptocurrency.utils

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerView: RecyclerView) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null
            override fun describeTo(description: Description) {
                var idDescription = recyclerView.id.toString()
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(recyclerView.id)
                    } catch (var4: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            Integer.valueOf(recyclerView.id)
                        )
                    }

                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {
                this.resources = view.resources
                if (childView == null) {
                    childView =
                        recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                }
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }
}