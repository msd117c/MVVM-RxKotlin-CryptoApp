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
import crypto.msd117c.com.cryptocurrency.modules.main.ui.adapter.RecyclerViewAdapter
import crypto.msd117c.com.cryptocurrency.util.Constants
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        ActivityComponentImplementation((application as CryptoApp).viewModelComponent)
    }

    private var alertDialog: AlertDialog? = null
    private lateinit var binding: MainActivityBinding
    private val viewModel by lazy {
        component.mainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        configureViewModel()
        configureView()
        initActivity()
    }

    fun configureViewModel() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is ViewModelStates.Loading ->
                    binding.swipe.isRefreshing = true

                is ViewModelStates.Loaded ->
                    binding.swipe.isRefreshing = false

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
            binding.list.adapter =
                RecyclerViewAdapter(list)
        })
    }

    fun configureView() {
        binding.list.layoutManager =
            LinearLayoutManager(this)

        binding.swipe.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    fun initActivity() {
        viewModel.loadData()
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

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }

}
