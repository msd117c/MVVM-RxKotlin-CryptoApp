package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Observer
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.Toast
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.NetworkManager

class MainLifeCycle(private val activity: MainActivity, private val lifecycle: Lifecycle, private val viewModel: MainViewModel): LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun retrieveAndPopulateList() {
        if (NetworkManager.verifyAvailableNetwork(activity)) {
            viewModel.reloadData().observe(activity, Observer {
                Toast.makeText(activity, "HECHO", Toast.LENGTH_LONG).show()
            })
        } else {

        }
    }
}