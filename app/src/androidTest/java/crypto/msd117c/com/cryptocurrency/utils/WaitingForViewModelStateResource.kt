package crypto.msd117c.com.cryptocurrency.utils

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates

class WaitingForViewModelStateResource(state: MutableLiveData<*>, activity: AppCompatActivity) :
    IdlingResource {

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    private var loadingEnded = false

    init {
        activity.runOnUiThread {
            state.observeForever {
                loadingEnded = it !is ViewModelStates.Loading
            }
        }
    }

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        if (loadingEnded && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }

        return loadingEnded && resourceCallback != null
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

}