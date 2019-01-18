package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.arch.lifecycle.*
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.LOADED
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.LOADING
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
            retrieveData()
        }

        viewModel.state.observe(activity, Observer {
            when(it) {
                LOADING -> {
                    activity.getBinding().list.adapter = null
                    activity.getBinding().swipe.isRefreshing = true
                }
                LOADED -> {
                    activity.getBinding().list.adapter = null
                    activity.getBinding().list.adapter = RecyclerViewAdapter(viewModel.getData(), this)
                    activity.getBinding().swipe.isRefreshing = false
                }
                ERROR -> {
                    activity.getBinding().list.adapter = null
                    AlertDialog.Builder(activity).setMessage("There was a problem while loading data. Please retry")
                        .setPositiveButton("Retry") {_, _ -> retrieveData() }
                        .setNegativeButton("Quit") {_, _ -> activity.finish()}
                        .show()
                    activity.getBinding().swipe.isRefreshing = false
                }
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeActivity() {
        retrieveData()
    }

    private fun retrieveData() {
        if (NetworkManager.verifyAvailableNetwork(activity)) {
            viewModel.loadData()
        } else {
            AlertDialog.Builder(activity).setMessage("There is no Internet")
                .setPositiveButton("Retry") {_, _ -> viewModel.loadData() }
                .setNegativeButton("Quit") {_, _ -> activity.finish()}
                .show()
        }
    }

    override fun onListFragmentInteraction(item: Coin?) {
        val name = item!!.getName()
        Toast.makeText(activity, name, Toast.LENGTH_LONG).show()
    }
}