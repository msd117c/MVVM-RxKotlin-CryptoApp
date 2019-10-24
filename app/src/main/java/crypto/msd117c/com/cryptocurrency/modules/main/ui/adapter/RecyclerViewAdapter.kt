package crypto.msd117c.com.cryptocurrency.modules.main.ui.adapter

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.ItemLayoutBinding
import crypto.msd117c.com.cryptocurrency.domain.coins.model.Datum
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.adapter.CoinViewModel

class RecyclerViewAdapter(
    var mValues: List<Datum>
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLayoutBinding>(
            LayoutInflater.from(parent.context)
            , R.layout.item_layout, parent, false
        )
        return ViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[holder.adapterPosition]

        holder.setViewModel(
            CoinViewModel(
                item
            )
        )
        holder.itemView.setOnClickListener {
            val shouldExpand = holder.binding.details.visibility == GONE

            val transition = ChangeBounds()
            transition.duration = 200

            if (shouldExpand) {
                expandView(holder)
            } else {
                expandedPosition = -1
                holder.binding.details.visibility = GONE
            }

            TransitionManager.beginDelayedTransition(holder.parent, transition)
        }
    }

    private fun expandView(
        holder: ViewHolder
    ) {
        if (expandedPosition != -1 && expandedPosition != holder.adapterPosition) {
            if ((holder.parent as RecyclerView).findViewHolderForAdapterPosition(expandedPosition) != null) {
                ((holder.parent).findViewHolderForAdapterPosition(expandedPosition) as ViewHolder)
                    .binding.details.visibility = GONE
            }
        }
        expandedPosition = holder.adapterPosition
        holder.binding.details.visibility = VISIBLE

        val manager = (holder.parent as RecyclerView).layoutManager
        val viewHolderHeight = holder.binding.itemRow.height

        val firstVisiblePos =
            (holder.parent.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

        val relativePosition = holder.adapterPosition - firstVisiblePos
        val remain = itemCount - firstVisiblePos + 1
        val availableHeight = remain * viewHolderHeight
        if (availableHeight >= viewHolderHeight + holder.binding.details.height + relativePosition *
            viewHolderHeight
        ) {
            (manager as LinearLayoutManager).scrollToPositionWithOffset(holder.adapterPosition, 0)
        } else {
            val height = holder.parent.height
            val distance = holder.itemView.height + holder.binding.details.height
            (manager as LinearLayoutManager).scrollToPositionWithOffset(
                holder.adapterPosition,
                height - distance
            )
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val binding: ItemLayoutBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root) {

        fun setViewModel(coin: CoinViewModel) {
            binding.coin = coin

            // Depending on the coin's growth we assign a color and a drawable object for the view
            val drawable = when (coin.isPositiveBalance()) {
                1 -> itemView.context.getDrawable(R.drawable.ic_action_trending_up)
                -1 -> itemView.context.getDrawable(R.drawable.ic_action_trending_down)
                else -> null
            }
            val color = when (coin.isPositiveBalance()) {
                1 -> {
                    Color.GREEN
                }
                -1 -> {
                    Color.RED
                }
                else -> {
                    Color.GRAY
                }
            }
            binding.percentage.setBackgroundColor(color)
            binding.growth.setImageDrawable(drawable)

            binding.details.visibility = GONE
        }
    }
}
