package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.ItemLayoutBinding
import crypto.msd117c.com.cryptocurrency.model.Coin
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.CoinViewModel
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_item.view.*


class RecyclerViewAdapter(
    private val mValues: List<Coin>,
    private val mListener: OnListFragmentInteractionListener?
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var expandedPosition = -1

    init {
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
        val item = mValues[position]

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

        holder.setViewModel(CoinViewModel(item))
        holder.itemDataBinding.itemRow.setOnClickListener {
            when(holder.itemDataBinding.details.visibility) {
                View.GONE -> {
                    holder.itemView.isActivated = true
                    holder.itemDataBinding.details.visibility = View.VISIBLE
                    holder.itemDataBinding.separator2.visibility = View.VISIBLE
                    if (expandedPosition != -1 && expandedPosition != position)
                    {
                        if ((holder.parent as RecyclerView).findViewHolderForAdapterPosition(expandedPosition) != null) {
                            holder.parent.findViewHolderForAdapterPosition(expandedPosition)!!.itemView.isActivated =
                                    false
                            ((holder.parent).findViewHolderForAdapterPosition(expandedPosition) as ViewHolder).itemDataBinding.details.visibility =
                                    View.GONE
                            ((holder.parent).findViewHolderForAdapterPosition(expandedPosition) as ViewHolder).itemDataBinding.separator2.visibility =
                                    View.GONE
                        }
                    }
                    holder.itemDataBinding.details.animate().alpha(1f).startDelay = 500
                    holder.itemDataBinding.separator2.animate().alpha(1f).startDelay = 500
                    expandedPosition = position
                    val manager = (holder.parent as RecyclerView).layoutManager
                    val distance: Int
                    val first = holder.parent.getChildAt(0)
                    val height = first.height
                    val current = holder.parent.getChildAdapterPosition(first)
                    val p = Math.abs(position - current)
                    distance = if (p > 5)
                        (p - (p - 5)) * height
                    else
                        p * height

                    (manager as LinearLayoutManager).scrollToPositionWithOffset(position, distance)
                }
                else -> {
                    holder.itemView.isActivated = false
                    holder.itemDataBinding.details.visibility = View.GONE
                    holder.itemDataBinding.separator2.visibility = View.GONE
                    holder.itemDataBinding.details.alpha = 0f
                    holder.itemDataBinding.separator2.alpha = 0f
                }
            }
            TransitionManager.beginDelayedTransition(holder.parent)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View, val parent: ViewGroup) : RecyclerView.ViewHolder(mView) {
        val itemDataBinding: ItemLayoutBinding = DataBindingUtil.bind(mView)!!

        fun setViewModel(coin: CoinViewModel) {
            itemDataBinding.coin = coin
            val drawable = when(coin.isPositiveBalance()) {
                1 -> mView.context.getDrawable(R.drawable.ic_action_trending_up)
                -1 -> mView.context.getDrawable(R.drawable.ic_action_trending_down)
                else -> null
            }
            val color = when(coin.isPositiveBalance()) {
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
            itemDataBinding.percentage.setTextColor(color)
            itemDataBinding.growth.setImageDrawable(drawable)
            itemDataBinding.details.visibility = View.GONE
            itemDataBinding.separator2.visibility = View.GONE
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Coin?)
    }
}
