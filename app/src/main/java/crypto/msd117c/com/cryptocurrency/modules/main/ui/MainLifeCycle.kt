package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.arch.lifecycle.*
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.NetworkManager
import javax.inject.Inject

class MainLifeCycle @Inject constructor(private val activity: MainActivity) : LifecycleObserver,
    RecyclerViewAdapter.OnListFragmentInteractionListener {
    init {
        activity.lifecycle.addObserver(this)
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private val columnCount = 1

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setup() {
        viewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)

        activity.getBinding().list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(activity)
            else -> GridLayoutManager(activity, columnCount)
        }
        activity.getBinding().swipe.setOnRefreshListener {
            checkConnectionAndLoadData()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeActivity() {
        checkConnectionAndLoadData()
    }

    private fun checkConnectionAndLoadData() {
        activity.getBinding().list.adapter = null
        if (NetworkManager.verifyAvailableNetwork(activity)) {
            retrieveAndPopulateList()
        } else {
            AlertDialog.Builder(activity).setMessage("There is no Internet")
                .setPositiveButton("Retry") {_, _ -> checkConnectionAndLoadData() }
                .setNegativeButton("Quit") {_, _ -> activity.finish()}
                .show()
            activity.getBinding().swipe.isRefreshing = false
        }
    }

    private fun retrieveAndPopulateList() = viewModel.coinsList.observe(activity, Observer {
        if (it != null && it.isNotEmpty()) {
            activity.getBinding().list.adapter = RecyclerViewAdapter(it, this)
        } else {
            AlertDialog.Builder(activity).setMessage("Error retrieving data, please refresh")
                .setNeutralButton("Ok", null)
                .show()
        }
        activity.getBinding().swipe.isRefreshing = false
    })

    override fun onListFragmentInteraction(item: Coin?) {
        val name = item!!.getName()
        Toast.makeText(activity, name, Toast.LENGTH_LONG).show()
    }
}