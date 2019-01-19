package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.arch.lifecycle.*
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.UNKNOWN_ERROR
import crypto.msd117c.com.cryptocurrency.util.NetworkManager
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import javax.inject.Inject

class MainLifeCycle @Inject constructor(private val activity: MainActivity) : LifecycleObserver,
    RecyclerViewAdapter.OnListFragmentInteractionListener {
    init {
        activity.lifecycle.addObserver(this)
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private val columnCount = 1
    private lateinit var alertDialog: AlertDialog

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setup() {
        configureViewModel()
        configureView()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)
        viewModel.state.observe(activity, Observer {
            when (it) {
                is ViewModelStates.Loading -> {
                    activity.getBinding().swipe.isRefreshing = true
                }
                is ViewModelStates.Loaded -> {
                    activity.getBinding().list.adapter = RecyclerViewAdapter(viewModel.getData(), this)
                    activity.getBinding().swipe.isRefreshing = false
                }
                is ViewModelStates.Error -> {
                    activity.getBinding().swipe.isRefreshing = false
                    when (it.type) {
                        NO_CONNECTION_ERROR -> showDialog(NO_CONNECTION_ERROR)
                        DATA_ERROR -> showDialog(DATA_ERROR)
                        else -> showDialog(UNKNOWN_ERROR)
                    }
                }
            }
        })
    }

    private fun configureView() {
        activity.getBinding().list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(activity)
            else -> GridLayoutManager(activity, columnCount)
        }
        activity.getBinding().swipe.setOnRefreshListener {
            viewModel.retrieveResponse()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeActivity() {
        viewModel.loadData(NetworkManager.verifyAvailableNetwork(activity))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopView() {
        dismissDialog()
        activity.getBinding().swipe.isRefreshing = false
    }

    override fun onListFragmentInteraction(item: Coin?) {
        Toast.makeText(activity, item!!.getName(), Toast.LENGTH_LONG).show()
    }

    // Auxiliary Functions

    private fun showDialog(errorType: Int) {
        alertDialog = AlertDialog.Builder(activity)
            .setMessage(
                when (errorType) {
                    NO_CONNECTION_ERROR -> activity.getString(R.string.no_connection)
                    DATA_ERROR -> activity.getString(R.string.data_error)
                    else -> activity.getString(R.string.unknown_error)
                }
            )
            .setPositiveButton(activity.getString(R.string.retry)) { _, _ -> viewModel.retrieveResponse() }
            .setNegativeButton(activity.getString(R.string.exit)) { _, _ -> activity.finish() }
            .setCancelable(false)
            .show()
    }

    private fun dismissDialog() {
        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

    //
}