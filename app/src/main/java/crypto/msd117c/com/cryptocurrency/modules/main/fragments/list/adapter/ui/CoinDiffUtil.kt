package crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.ui

import androidx.recyclerview.widget.DiffUtil
import crypto.msd117c.com.cryptocurrency.modules.main.fragments.list.adapter.viewmodel.CoinViewModel

class CoinDiffUtil(
    private val oldItemsList: List<CoinViewModel>,
    private val newItemsList: List<CoinViewModel>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemsList[oldItemPosition].getId() == newItemsList[newItemPosition].getId()

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemsList[oldItemPosition].getCoinPercentage() == newItemsList[newItemPosition].getCoinPercentage()

}