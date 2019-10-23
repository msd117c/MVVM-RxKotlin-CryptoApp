package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.MainActivityBinding
import crypto.msd117c.com.cryptocurrency.di.CryptoApp
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelComponentImplementation
import crypto.msd117c.com.cryptocurrency.modules.main.ui.adapter.RecyclerViewAdapter
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.Constants
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        ViewModelComponentImplementation((application as CryptoApp).repositoryComponent)
    }

    private var alertDialog: AlertDialog? = null
    private var listAdapter: RecyclerViewAdapter? = null

    private lateinit var binding: MainActivityBinding
    private val viewModelFactory by lazy {
        component.viewModelFactory
    }
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        configureViewModel()
        configureView()
        initActivity()
    }

    fun configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.apply {
            state.observe(this@MainActivity, Observer {
                when (it) {
                    is ViewModelStates.Loading ->
                        binding.swipe.isRefreshing = true

                    is ViewModelStates.Loaded ->
                        binding.swipe.isRefreshing = false

                    is ViewModelStates.Error -> {
                        binding.swipe.isRefreshing = false
                        showAlertDialog(it.type)
                    }
                }
            })
            list.observe(this@MainActivity, Observer { list ->
                listAdapter?.mValues = list
                listAdapter?.notifyDataSetChanged()
            })
        }
    }

    fun configureView() {
        listAdapter = RecyclerViewAdapter(mutableListOf())

        binding.list.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity)
            adapter = listAdapter

        }
        binding.swipe.setOnRefreshListener {
            viewModel.loadData(true)
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
