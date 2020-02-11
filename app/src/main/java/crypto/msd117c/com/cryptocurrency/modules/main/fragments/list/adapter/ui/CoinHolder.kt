package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.ui

import androidx.recyclerview.widget.RecyclerView
import crypto.msd117c.com.cryptocurrency.databinding.ItemViewCoinGridBinding
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.viewmodel.CoinViewModel

class CoinHolder(private val binding: ItemViewCoinGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: CoinViewModel) {
        binding.coin = coin
    }

}
