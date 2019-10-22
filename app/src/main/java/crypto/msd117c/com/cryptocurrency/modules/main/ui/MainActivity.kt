package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.MainActivityBinding
import crypto.msd117c.com.cryptocurrency.di.CryptoApp
import crypto.msd117c.com.cryptocurrency.di.activity.ActivityComponentImplementation
import crypto.msd117c.com.cryptocurrency.util.Constants
import crypto.msd117c.com.cryptocurrency.util.GlobalValues
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        ActivityComponentImplementation((application as CryptoApp).viewModelComponent)
    }

    private lateinit var alertDialog: AlertDialog
    private lateinit var binding: MainActivityBinding
    private val viewModel by lazy {
        component.mainViewModel
    }
    private lateinit var lifeCycle: MainLifeCycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        lifeCycle = MainLifeCycle(this)
    }

    fun configureViewModel() {
        // Update UI according to ViewModel's state
        viewModel.state.observe(this, Observer {
            when (it) {
                is ViewModelStates.Loading -> {
                    // Clear the adapter
                    binding.list.adapter = null
                    binding.swipe.isRefreshing = true
                }
                is ViewModelStates.Loaded -> {
                    binding.swipe.isRefreshing = false
                }
                is ViewModelStates.Error -> {
                    binding.swipe.isRefreshing = false
                    when (it.type) {
                        Constants.NO_CONNECTION_ERROR -> showAlertDialog(Constants.NO_CONNECTION_ERROR)
                        Constants.DATA_ERROR -> showAlertDialog(Constants.DATA_ERROR)
                        else -> showAlertDialog(Constants.UNKNOWN_ERROR)
                    }
                }
            }
        })
        viewModel.list.observe(this, Observer { list ->
            binding.list.adapter = RecyclerViewAdapter(list)
        })
    }

    fun configureView() {
        binding.list.layoutManager =
            LinearLayoutManager(this)
        // Clear the adapter when create all
        binding.list.adapter = null

        binding.swipe.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    // Auxiliary Functions to make the code more clear
    fun setGlobalValues() {
        GlobalValues.decimalSeparator = getString(R.string.decimal_separator)
        GlobalValues.thousandSeparator = getString(R.string.thounsand_separator)
    }

    fun checkData() {
        if (binding.list.adapter == null) {
            viewModel.loadData()
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
            .setPositiveButton(getString(R.string.retry)) { _, _ ->
                viewModel.loadData()
            }
            .setNegativeButton(getString(R.string.exit)) { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    fun dismissDialog() {
        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

    fun getBinding(): MainActivityBinding = binding
}
