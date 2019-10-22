package crypto.msd117c.com.cryptocurrency.modules.main.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MainLifeCycle : LifecycleObserver {

    private lateinit var activity: MainActivity

    fun initLifeCycle(activity: MainActivity) {
        this.activity = activity
        activity.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setup() {
        activity.configureViewModel()
        activity.configureView()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeActivity() {
        activity.setGlobalValues()
        activity.checkData()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopView() {
        activity.dismissDialog()
        activity.getBinding().swipe.isRefreshing = false
    }
}