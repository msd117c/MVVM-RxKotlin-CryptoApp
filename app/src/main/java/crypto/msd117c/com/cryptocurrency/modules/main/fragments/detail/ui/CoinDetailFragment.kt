package crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.ui

import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.FragmentCoinDetailBinding
import crypto.msd117c.com.cryptocurrency.di.CryptoApp
import crypto.msd117c.com.cryptocurrency.di.viewmodel.ViewModelFactory
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.adapter.TagsAdapter
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.adapter.UrlsAdapter
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.detail.viewmodel.*
import kotlinx.android.synthetic.main.fragment_coin_detail.*
import kotlinx.android.synthetic.main.fragment_coins_list.error_layout
import kotlinx.android.synthetic.main.fragment_coins_list.error_message
import kotlinx.android.synthetic.main.fragment_coins_list.loading_layout
import kotlinx.android.synthetic.main.include_coin_detail.*
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    @Inject
    lateinit var coinsDetailFragmentViewModelFactory: ViewModelFactory<CoinDetailFragmentViewModel>

    private lateinit var coinDetailFragmentViewModel: CoinDetailFragmentViewModel

    @Inject
    lateinit var tagsAdapter: TagsAdapter

    @Inject
    lateinit var urlsAdapter: UrlsAdapter

    private lateinit var binding: FragmentCoinDetailBinding

    val args: CoinDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        (context?.applicationContext as CryptoApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        if (args.coinId == -1) {
            requireActivity().onBackPressed()
        }

        coinDetailFragmentViewModel =
            ViewModelProvider(
                this,
                coinsDetailFragmentViewModelFactory
            )[CoinDetailFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_coin_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
        configureViewModel()

        coinDetailFragmentViewModel.getCoinDetail(args.coinId)
    }

    private fun configureView() {
        /*tags_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tagsAdapter
        }*/

        urls_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = urlsAdapter
        }
    }

    private fun configureViewModel() {
        coinDetailFragmentViewModel.getState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                Loading -> {
                    loading_layout.visibility = VISIBLE
                    error_layout.visibility = GONE
                    coin_detail_layout.visibility = GONE
                }
                is Loaded -> {
                    loading_layout.visibility = GONE
                    error_layout.visibility = GONE
                    coin_detail_layout.visibility = VISIBLE
                    binding.coin = state.coinDetailUiModel.coin
                    //tagsAdapter.setItems(state.coinDetailUiModel.coin.getTags())
                    state.coinDetailUiModel.coin.getTags().forEach { tag ->
                        val chip = Chip(context)
                        chip.text = tag
                        tags_group.addView(chip)
                    }
                    urlsAdapter.setItems(state.coinDetailUiModel.coin.getUrls())
                }
                is Error -> {
                    loading_layout.visibility = GONE
                    error_layout.visibility = VISIBLE
                    coin_detail_layout.visibility = GONE
                    error_message.text = when (state.error) {
                        NoData -> getString(R.string.data_error)
                        NoConnection -> getString(R.string.no_connection)
                        UnknownError -> getString(R.string.unknown_error)
                    }
                }
            }
        })
    }

}