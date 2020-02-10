package crypto.msd117c.com.cryptocurrency.base.ui

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import crypto.msd117c.com.cryptocurrency.R

@BindingAdapter("growthIcon")
fun bindGrowthIcon(imageView: ImageView?, isPositive: Int) {
    imageView?.let {
        when (isPositive) {
            1 -> imageView.context.getDrawable(R.drawable.ic_action_trending_up)
            -1 -> imageView.context.getDrawable(R.drawable.ic_action_trending_down)
            else -> null
        }?.let {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        }
    }
}

@BindingAdapter("percentageColor")
fun bindPercentageColor(textView: TextView?, isPositive: Int) {
    textView?.let {
        textView.setTextColor(
            when (isPositive) {
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
        )
    }
}

@BindingAdapter("coinIcon")
fun bindCoinIcon(imageView: ImageView?, iconUrl: String?) {
    imageView?.let {
        iconUrl?.let {
            Glide.with(imageView.context)
                .load(iconUrl)
                .into(imageView)
        }
    }
}