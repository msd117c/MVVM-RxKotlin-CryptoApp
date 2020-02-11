package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.constants.NavigationConstants
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.viewmodel.CoinViewModel
import javax.inject.Inject

class CoinsAdapter @Inject constructor() : RecyclerView.Adapter<CoinHolder>() {

    private val listOfCoins = mutableListOf<CoinViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHolder =
        CoinHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_view_coin_grid,
                parent, false
            )
        )

    override fun getItemCount(): Int = listOfCoins.size

    override fun onBindViewHolder(holder: CoinHolder, position: Int) {
        holder.bind(listOfCoins[position])
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(NavigationConstants.NAVIGATION_COIN_ID, listOfCoins[position].getId())
            holder.itemView.findNavController()
                .navigate(R.id.action_coinsListFragment_to_coinDetailFragment, bundle)
        }
    }

    fun setItems(listOfCoins: List<CoinViewModel>) {
        val diffCallback = CoinDiffUtil(this.listOfCoins, listOfCoins)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listOfCoins.clear()
        this.listOfCoins.addAll(listOfCoins)
        diffResult.dispatchUpdatesTo(this)
    }
}