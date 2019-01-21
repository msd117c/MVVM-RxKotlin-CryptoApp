package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.arch.lifecycle.*
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.DATA_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.NO_CONNECTION_ERROR
import crypto.msd117c.com.cryptocurrency.util.Constants.Companion.UNKNOWN_ERROR
import crypto.msd117c.com.cryptocurrency.util.GlobalValues
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
    private lateinit var alertDialog: AlertDialog

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setup() {
        configureViewModel()
        configureView()
    }

    private fun configureViewModel() {
        viewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)

        // Update UI according to ViewModel's state
        viewModel.state.observe(activity, Observer {
            when (it) {
                is ViewModelStates.Loading -> {
                    // Clear the adapter
                    activity.getBinding().list.adapter = null
                    activity.getBinding().swipe.isRefreshing = true
                }
                is ViewModelStates.Loaded -> {
                    // Clear the adapter
                    activity.getBinding().list.adapter = null
                    // Load the adapter
                    activity.getBinding().list.adapter = RecyclerViewAdapter(it.list, this)
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
        activity.getBinding().list.layoutManager = LinearLayoutManager(activity)
        // Clear the adapter when create all
        activity.getBinding().list.adapter = null

        activity.getBinding().swipe.setOnRefreshListener {
            viewModel.retrieveResponse()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeActivity() {
        setGlobalValues()
        checkData()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopView() {
        dismissDialog()
        activity.getBinding().swipe.isRefreshing = false
    }

    override fun onListFragmentInteraction(item: Coin?) {
        Toast.makeText(activity, item!!.getName(), Toast.LENGTH_LONG).show()
    }

    // Auxiliary Functions to make the code more clear
    private fun setGlobalValues() {
        GlobalValues.decimalSeparator = activity.getString(R.string.decimal_separator)
        GlobalValues.thousandSeparator = activity.getString(R.string.thounsand_separator)
    }

    private fun checkData() {
        if (activity.getBinding().list.adapter == null) {
            viewModel.loadData(NetworkManager.verifyAvailableNetwork(activity))
        }
    }

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
}