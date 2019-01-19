package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.ItemLayoutBinding
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.CoinViewModel


class RecyclerViewAdapter(
    private val mValues: List<Coin>,
    private val mListener: OnListFragmentInteractionListener?
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var stateList = BooleanArray(mValues.size)
    private var expandedPosition = -1

    init {
        stateList.all { false }
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Coin
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[holder.adapterPosition]

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

        holder.setViewModel(CoinViewModel(item))
        holder.mView.setOnClickListener {
            val shouldExpand = holder.itemDataBinding.details.visibility == View.GONE

            val transition = ChangeBounds()
            transition.duration = 200

            if (shouldExpand) {
                if (expandedPosition != -1 && expandedPosition != holder.adapterPosition) {
                    if ((holder.parent as RecyclerView).findViewHolderForAdapterPosition(expandedPosition) != null) {
                        ((holder.parent).findViewHolderForAdapterPosition(expandedPosition) as ViewHolder)
                            .itemDataBinding.details.visibility = View.GONE
                        stateList.all { false }
                    }
                }
                expandedPosition = holder.adapterPosition
                holder.itemDataBinding.details.visibility = VISIBLE

                val manager = (holder.parent as RecyclerView).layoutManager
                val viewHolderHeight = holder.itemDataBinding.itemRow.height

                val firstVisiblePos =
                    (holder.parent.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                val relativePosition = holder.adapterPosition - firstVisiblePos
                val remain = itemCount - firstVisiblePos + 1
                val availableHeight = remain * viewHolderHeight
                if (availableHeight >= viewHolderHeight + holder.itemDataBinding.details.height + relativePosition *
                    viewHolderHeight
                ) {
                    (manager as LinearLayoutManager).scrollToPositionWithOffset(holder.adapterPosition, 0)
                } else {
                    val height = holder.parent.height
                    val distance = holder.mView.height + holder.itemDataBinding.details.height
                    (manager as LinearLayoutManager).scrollToPositionWithOffset(
                        holder.adapterPosition,
                        height - distance
                    )
                }
            } else {
                expandedPosition = -1
                holder.itemDataBinding.details.visibility = GONE
            }

            TransitionManager.beginDelayedTransition(holder.parent, transition)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View, val parent: ViewGroup) : RecyclerView.ViewHolder(mView) {
        val itemDataBinding: ItemLayoutBinding = DataBindingUtil.bind(mView)!!

        fun setViewModel(coin: CoinViewModel) {
            itemDataBinding.coin = coin
            val drawable = when (coin.isPositiveBalance()) {
                1 -> mView.context.getDrawable(R.drawable.ic_action_trending_up)
                -1 -> mView.context.getDrawable(R.drawable.ic_action_trending_down)
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
            itemDataBinding.percentage.setBackgroundColor(color)
            itemDataBinding.growth.setImageDrawable(drawable)
            itemDataBinding.details.visibility = View.GONE
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Coin?)
    }
}
