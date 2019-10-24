package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.MainActivityBinding
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelFactory
import crypto.msd117c.com.cryptocurrency.modules.main.ui.adapter.RecyclerViewAdapter
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import crypto.msd117c.com.cryptocurrency.util.Constants
import crypto.msd117c.com.cryptocurrency.util.ViewModelStates
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var alertDialog: AlertDialog? = null
    private var listAdapter: RecyclerViewAdapter? = null

    private lateinit var binding: MainActivityBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)

        configureViewModel()
        configureView()
        initActivity()
    }

    fun configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        viewModel.state.observe(this, Observer {
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
        viewModel.list.observe(this, Observer { list ->
            list?.let { nonNullList ->
                listAdapter?.mValues = nonNullList
                listAdapter?.notifyDataSetChanged()
            }
        })
    }

    fun configureView() {
        listAdapter = RecyclerViewAdapter(
            mutableListOf()
        )

        binding.list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
        binding.swipe.setOnRefreshListener {
            viewModel.retrieveResponse(true)
        }
    }

    fun initActivity() {
        viewModel.retrieveResponse()
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
                viewModel.retrieveResponse(true)
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
