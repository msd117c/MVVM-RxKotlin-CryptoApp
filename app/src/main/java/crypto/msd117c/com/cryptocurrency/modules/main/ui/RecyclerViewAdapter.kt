package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
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

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Coin
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

        holder.setViewModel(CoinViewModel(item))
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val itemDataBinding: ItemLayoutBinding = DataBindingUtil.bind(mView)!!

        fun setViewModel(coin: CoinViewModel) {
            itemDataBinding.coin = coin
            val color = when(coin.isPositiveBalance()) {
                1 -> Color.GREEN
                -1 -> Color.RED
                else -> Color.GRAY
            }
            itemDataBinding.percentage.setTextColor(color)
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Coin?)
    }
}
