package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.FragmentCoinsListBinding
import crypto.msd117c.com.cryptocurrency.di.CryptoApp
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelFactory
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.ui.CoinsAdapter
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.viewmodel.*
import kotlinx.android.synthetic.main.fragment_coins_list.*
import javax.inject.Inject

class CoinsListFragment : Fragment() {

    @Inject
    lateinit var coinsListViewModelFactory: ViewModelFactory<CoinsListViewModel>

    private lateinit var coinsListViewModel: CoinsListViewModel

    @Inject
    lateinit var coinsAdapter: CoinsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (context?.applicationContext as CryptoApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        coinsListViewModel =
            ViewModelProvider(this, coinsListViewModelFactory)[CoinsListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentCoinsListBinding>(
            LayoutInflater.from(context),
            R.layout.fragment_coins_list,
            container,
            false
        ).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
        configureViewModel()

        coinsListViewModel.getLatestCoins()
    }

    private fun configureView() {
        coins_swipe_list.visibility = GONE

        coins_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coinsAdapter
        }
    }

    private fun configureViewModel() {
        coinsListViewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                Loading -> {
                    loading_layout.visibility = VISIBLE
                    error_layout.visibility = GONE
                    coins_swipe_list.visibility = GONE
                }
                is Loaded -> {
                    loading_layout.visibility = GONE
                    error_layout.visibility = GONE
                    coins_swipe_list.visibility = VISIBLE
                    coinsAdapter.setItems(state.mainUiModel.listOfCoins)
                }
                is Error -> {
                    loading_layout.visibility = GONE
                    error_layout.visibility = VISIBLE
                    coins_swipe_list.visibility = GONE
                    error_message.text = when (state.error) {
                        EmptyList -> getString(R.string.data_error)
                        NoConnection -> getString(R.string.no_connection)
                        UnknownError -> getString(R.string.unknown_error)
                    }
                }
            }
        })
    }
}