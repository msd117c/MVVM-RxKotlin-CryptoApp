package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.MainActivityBinding
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelFactory
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.repository.RetrofitFactory
import crypto.msd117c.com.cryptocurrency.util.Constants
import crypto.msd117c.com.cryptocurrency.util.GlobalValues
import crypto.msd117c.com.cryptocurrency.util.NetworkManager
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnListFragmentInteractionListener {

    @Inject
    lateinit var lifeCycle: MainLifeCycle

    @Inject
    lateinit var retrofitFactory: RetrofitFactory

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var alertDialog: AlertDialog
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }

    fun configureViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(retrofitFactory)).get(MainViewModel::class.java)

        // Update UI according to ViewModel's state
        viewModel.state.observe(this, Observer {
            when (it) {
                is ViewModelStates.Loading -> {
                    // Clear the adapter
                    mainActivityBinding.list.adapter = null
                    mainActivityBinding.swipe.isRefreshing = true
                }
                is ViewModelStates.Loaded -> {
                    // Clear the adapter
                    getBinding().list.adapter = null
                    // Load the adapter
                    getBinding().list.adapter = RecyclerViewAdapter(it.list, this)
                    getBinding().swipe.isRefreshing = false
                }
                is ViewModelStates.Error -> {
                    getBinding().swipe.isRefreshing = false
                    when (it.type) {
                        Constants.NO_CONNECTION_ERROR -> showAlertDialog(Constants.NO_CONNECTION_ERROR)
                        Constants.DATA_ERROR -> showAlertDialog(Constants.DATA_ERROR)
                        else -> showAlertDialog(Constants.UNKNOWN_ERROR)
                    }
                }
            }
        })
    }

    fun configureView() {
        mainActivityBinding.list.layoutManager = LinearLayoutManager(this)
        // Clear the adapter when create all
        mainActivityBinding.list.adapter = null

        mainActivityBinding.swipe.setOnRefreshListener {
            viewModel.retrieveResponse()
        }
    }

    fun getBinding(): MainActivityBinding {
        return mainActivityBinding
    }


    override fun onListFragmentInteraction(item: Coin?) {
        Toast.makeText(this, item!!.getName(), Toast.LENGTH_LONG).show()
    }

    // Auxiliary Functions to make the code more clear
    fun setGlobalValues() {
        GlobalValues.decimalSeparator = getString(R.string.decimal_separator)
        GlobalValues.thousandSeparator = getString(R.string.thounsand_separator)
    }

    fun checkData() {
        if (getBinding().list.adapter == null) {
            viewModel.loadData(NetworkManager.verifyAvailableNetwork(this))
        }
    }

    private fun showAlertDialog(errorType: Int) {
        alertDialog = AlertDialog.Builder(this)
            .setMessage(
                when (errorType) {
                    Constants.NO_CONNECTION_ERROR -> getString(R.string.no_connection)
                    Constants.DATA_ERROR -> getString(R.string.data_error)
                    else -> getString(R.string.unknown_error)
                }
            )
            .setPositiveButton(getString(R.string.retry)) { _, _ -> viewModel.retrieveResponse() }
            .setNegativeButton(getString(R.string.exit)) { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    fun dismissDialog() {
        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }
}
